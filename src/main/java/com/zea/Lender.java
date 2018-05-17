package com.zea;

public class Lender implements Comparable{
        private String name;
        private Double rate;
        private Double availableAmount;

        public Lender() {
        }

        public Lender(String name, Double rate, Double available) {
                this.name = name;
                this.rate = rate;
                this.availableAmount = available;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public Double getRate() {
                return rate;
        }

        public void setRate(Double rate) {
                this.rate = rate;
        }

        public Double getAvailable() {
                return availableAmount;
        }

        public void setAvailable(Double available) {
                this.availableAmount = available;
        }

        @Override
        public String toString() {
                return "Lender{" +
                        "name='" + name + '\'' +
                        ", rate=" + rate +
                        ", available=" + availableAmount +
                        '}';
        }

        @Override
        public int compareTo(Object o) {
                if(this.getRate() < ((Lender) o).getRate()){
                        return -1;
                }else if(this.getRate() > ((Lender) o).getRate()){
                        return 1;
                }else{
                        return 0;
                }
        }
}