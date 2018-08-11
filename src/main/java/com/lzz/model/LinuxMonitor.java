package com.lzz.model;

/**
 * Created by gl49 on 2017/11/7.
 */
public class LinuxMonitor {
    private float load_average;
    private int memory_total;
    private int memory_available;
    private int memory_free;
    private int memory_use;
    private int processor;
    private int netstat;
    private int ps_num;
    private int thread_num;
    private String top_1;
    private String top_2;
    private String top_3;

    private String ip;
    private String service;
    private int day;
    private int hour;
    private int minute;
    private int add_time;

    public float getLoad_average() {
        return load_average;
    }

    public void setLoad_average(float load_average) {
        this.load_average = load_average;
    }

    public int getMemory_total() {
        return memory_total;
    }

    public void setMemory_total(int memory_total) {
        this.memory_total = memory_total;
    }

    public int getMemory_available() {
        return memory_available;
    }

    public void setMemory_available(int memory_available) {
        this.memory_available = memory_available;
    }

    public int getMemory_free() {
        return memory_free;
    }

    public void setMemory_free(int memory_free) {
        this.memory_free = memory_free;
    }

    public int getMemory_use() {
        return memory_use;
    }

    public void setMemory_use(int memory_use) {
        this.memory_use = memory_use;
    }

    public int getProcessor() {
        return processor;
    }

    public void setProcessor(int processor) {
        this.processor = processor;
    }

    public int getNetstat() {
        return netstat;
    }

    public void setNetstat(int netstat) {
        this.netstat = netstat;
    }

    public int getPs_num() {
        return ps_num;
    }

    public void setPs_num(int ps_num) {
        this.ps_num = ps_num;
    }

    public int getThread_num() {
        return thread_num;
    }

    public void setThread_num(int thread_num) {
        this.thread_num = thread_num;
    }

    public String getTop_1() {
        return top_1;
    }

    public void setTop_1(String top_1) {
        this.top_1 = top_1;
    }

    public String getTop_2() {
        return top_2;
    }

    public void setTop_2(String top_2) {
        this.top_2 = top_2;
    }

    public String getTop_3() {
        return top_3;
    }

    public void setTop_3(String top_3) {
        this.top_3 = top_3;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getAdd_time() {
        return add_time;
    }

    public void setAdd_time(int add_time) {
        this.add_time = add_time;
    }

    @Override
    public String toString() {
        return "LinuxMonitor{" +
                "load_average=" + load_average +
                ", memory_total=" + memory_total +
                ", memory_available=" + memory_available +
                ", memory_free=" + memory_free +
                ", memory_use=" + memory_use +
                ", processor=" + processor +
                ", netstat=" + netstat +
                ", ps_num=" + ps_num +
                ", thread_num=" + thread_num +
                ", top_1='" + top_1 + '\'' +
                ", top_2='" + top_2 + '\'' +
                ", top_3='" + top_3 + '\'' +
                ", ip='" + ip + '\'' +
                ", service='" + service + '\'' +
                ", day=" + day +
                ", hour=" + hour +
                ", minute=" + minute +
                ", add_time=" + add_time +
                '}';
    }
}