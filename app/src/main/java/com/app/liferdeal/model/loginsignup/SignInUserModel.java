package com.app.liferdeal.model.loginsignup;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SignInUserModel {

        @SerializedName("user_address")
        @Expose
        private Object userAddress;
        @SerializedName("deliveryaddress")
        @Expose
        private List<Object> deliveryaddress = null;

        public Object getUserAddress() {
            return userAddress;
        }

        public void setUserAddress(Object userAddress) {
            this.userAddress = userAddress;
        }

        public List<Object> getDeliveryaddress() {
            return deliveryaddress;
        }

        public void setDeliveryaddress(List<Object> deliveryaddress) {
            this.deliveryaddress = deliveryaddress;
        }
}
