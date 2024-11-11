package org.acme.dto;

public class ProducerRanked {
    private String movie;
    private String producerName;
    private Integer previousWin;
    private Integer followingWin;
    private Long rankAsc;
    private Long rankDesc;
    private Boolean smallestRange;

    public ProducerRanked(String movie, String producerName, Integer previousWin, Integer followingWin, Long rankAsc, Long rankDesc, Boolean smallestRange) {
        this.movie = movie;
        this.producerName = producerName;
        this.previousWin = previousWin;
        this.followingWin = followingWin;
        this.rankAsc = rankAsc;
        this.rankDesc = rankDesc;
        this.smallestRange = smallestRange;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public String getProducerName() {
        return producerName;
    }

    public void setProducerName(String producerName) {
        this.producerName = producerName;
    }

    public Integer getPreviousWin() {
        return previousWin;
    }

    public void setPreviousWin(Integer previousWin) {
        this.previousWin = previousWin;
    }

    public Integer getFollowingWin() {
        return followingWin;
    }

    public void setFollowingWin(Integer followingWin) {
        this.followingWin = followingWin;
    }

    public Long getRankAsc() {
        return rankAsc;
    }

    public void setRankAsc(Long rankAsc) {
        this.rankAsc = rankAsc;
    }

    public Long getRankDesc() {
        return rankDesc;
    }

    public void setRankDesc(Long rankDesc) {
        this.rankDesc = rankDesc;
    }

    public Boolean getSmallestRange() {
        return smallestRange;
    }

    public void setSmallestRange(Boolean smallestRange) {
        this.smallestRange = smallestRange;
    }
}
