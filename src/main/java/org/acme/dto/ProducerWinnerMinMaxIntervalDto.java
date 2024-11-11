package org.acme.dto;

import java.util.ArrayList;
import java.util.List;

public class ProducerWinnerMinMaxIntervalDto {
    private List<ProducerWinnerIntervalDto> min = new ArrayList<>();
    private List<ProducerWinnerIntervalDto> max = new ArrayList<>();

    public List<ProducerWinnerIntervalDto> getMin() {
        return min;
    }

    public void setMin(List<ProducerWinnerIntervalDto> min) {
        this.min = min;
    }

    public List<ProducerWinnerIntervalDto> getMax() {
        return max;
    }

    public void setMax(List<ProducerWinnerIntervalDto> max) {
        this.max = max;
    }
}
