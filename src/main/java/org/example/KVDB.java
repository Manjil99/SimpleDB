package org.example;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class KVDB {
    private String _walFile = "wal_file_0.log";
    private final String _metadataFile = "wal_metadata.log";

    private int currentFileNo = 0;

    private final long compactionPeriod = 60;
    private HashMap<String,MetaData> _index = new HashMap<>();
    private RandomAccessFile randomAccessFileWriter = new RandomAccessFile(_walFile,"rw");
    private RandomAccessFile randomAccessFileReader = new RandomAccessFile(_walFile,"r");

    private BufferedWriter _metaDataWriter = new BufferedWriter(new FileWriter(new File(_metadataFile)));
    private BufferedReader _metaDataReader = new BufferedReader(new FileReader(new File(_metadataFile)));

    private class MetaData{
        private long offset;
        private long len;
        public MetaData(long offset,long len){
            this.offset = offset;
            this.len = len;
        }
        public long getLen() {
            return len;
        }
        public long getOffset() {
            return offset;
        }
        public void setLen(long len) {
            this.len = len;
        }
        public void setOffset(long offset){
            this.offset = offset;
        }
    }
    public KVDB() throws IOException {
        this.loadIndex();
    }
    private void loadIndex() throws IOException {
        randomAccessFileWriter.seek(0);
        String line;
        long currOffset = 0;
        while((line = randomAccessFileWriter.readLine()) != null){
            String[] commands = line.split(";");
            for(String stringCommand: commands){
                if(stringCommand == null)continue;
                Command command = new Command(stringCommand);
                String key = command.getKey();
                if(_index.containsKey(key)){
                    _index.get(key).setOffset(currOffset);
                    _index.get(key).setLen(stringCommand.length());
                } else {
                    _index.put(key,new MetaData(currOffset,stringCommand.length()));
                }
                currOffset += stringCommand.length() + 1;
            }
        }
    }
    public synchronized Command get(String key) throws IOException {
        if(!_index.containsKey(key)){
            return null; // throw KeyNotFound Exception here
        }
        MetaData metaData = _index.get(key);
        byte[] data = new byte[(int)metaData.getLen()];
        randomAccessFileReader.seek(metaData.getOffset());
        randomAccessFileReader.read(data,0,(int)metaData.getLen());
        Command command = new Command(new String(data));
        return command;
    }

    public synchronized void set(String stringCommand) throws IOException {
        Command command = new Command(stringCommand);
        String key = command.getKey();
        if(_index.containsKey(key)){
            _index.get(key).setOffset(randomAccessFileWriter.getFilePointer());
            _index.get(key).setLen(stringCommand.length());
        } else {
            _index.put(key,new MetaData(randomAccessFileWriter.getFilePointer(),stringCommand.length()));
        }
        randomAccessFileWriter.writeBytes(stringCommand);
        randomAccessFileWriter.writeBytes(";");
        System.out.println("Success!");

    }

    public void compaction() throws IOException {
        while (true) {
            try {
                Thread.sleep(compactionPeriod * 1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            synchronized (this) {
                currentFileNo++;
                _walFile = String.format("wal_log_%d.log", currentFileNo);
                randomAccessFileWriter = new RandomAccessFile(_walFile, "rw");
                HashMap<String, MetaData> nIndex = new HashMap<>();
                for (String key : _index.keySet()) {
                    Command command = this.get(key);
                    nIndex.put(key, new MetaData(randomAccessFileWriter.getFilePointer(), command.getCommand().length()));
                    randomAccessFileWriter.writeBytes(command.getCommand());
                    randomAccessFileWriter.writeBytes(";");
                }
                randomAccessFileReader = new RandomAccessFile(_walFile,"r");
                _index = nIndex;
            }
            System.out.println("Compaction Successful");
        }
    }
}
