package com.action.info_shadow;

public class contest_info{
    private String contest_name, contest_rank;

    public contest_info(String contest_name, String contest_rank){
        this.contest_name = contest_name;
        this.contest_rank = contest_rank;
    }

    public contest_info() {}


    public void setContest_name(String contest_name) {
        this.contest_name = contest_name;
    }

    public void setContest_rank(String contest_rank) {
        this.contest_rank = contest_rank;
    }

    public String getContest_name() {
        return contest_name;
    }

    public String getContest_rank() {
        return contest_rank;
    }

    public static contest_info static_generate(String name, String rank) {
        contest_info res = new contest_info();
        res.setContest_name(name);
        res.setContest_rank(rank);
        return res;
    }

    @Override
    public String toString(){
        return String.format("{contest_name:%s,rank:%s}", contest_name, contest_rank);
    }
}