package com.app.liferdeal.model.restaurant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoyalityPointsModel {

    @SerializedName("per_order_loyality_point")
    @Expose
    private Integer perOrderLoyalityPoint;
    @SerializedName("sign_points")
    @Expose
    private Integer signPoints;
    @SerializedName("place_first_orders_points")
    @Expose
    private Integer placeFirstOrdersPoints;
    @SerializedName("post_review_points")
    @Expose
    private Integer postReviewPoints;
    @SerializedName("place_group_ordering_points")
    @Expose
    private Integer placeGroupOrderingPoints;
    @SerializedName("birthday_celebrations_points")
    @Expose
    private Integer birthdayCelebrationsPoints;
    @SerializedName("social_media_sharing_points")
    @Expose
    private Integer socialMediaSharingPoints;
    @SerializedName("refer_friends_points")
    @Expose
    private Integer referFriendsPoints;
    @SerializedName("spend_more_than_points")
    @Expose
    private Integer spendMoreThanPoints;

    public Integer getPerOrderLoyalityPoint() {
        return perOrderLoyalityPoint;
    }

    public void setPerOrderLoyalityPoint(Integer perOrderLoyalityPoint) {
        this.perOrderLoyalityPoint = perOrderLoyalityPoint;
    }

    public Integer getSignPoints() {
        return signPoints;
    }

    public void setSignPoints(Integer signPoints) {
        this.signPoints = signPoints;
    }

    public Integer getPlaceFirstOrdersPoints() {
        return placeFirstOrdersPoints;
    }

    public void setPlaceFirstOrdersPoints(Integer placeFirstOrdersPoints) {
        this.placeFirstOrdersPoints = placeFirstOrdersPoints;
    }

    public Integer getPostReviewPoints() {
        return postReviewPoints;
    }

    public void setPostReviewPoints(Integer postReviewPoints) {
        this.postReviewPoints = postReviewPoints;
    }

    public Integer getPlaceGroupOrderingPoints() {
        return placeGroupOrderingPoints;
    }

    public void setPlaceGroupOrderingPoints(Integer placeGroupOrderingPoints) {
        this.placeGroupOrderingPoints = placeGroupOrderingPoints;
    }

    public Integer getBirthdayCelebrationsPoints() {
        return birthdayCelebrationsPoints;
    }

    public void setBirthdayCelebrationsPoints(Integer birthdayCelebrationsPoints) {
        this.birthdayCelebrationsPoints = birthdayCelebrationsPoints;
    }

    public Integer getSocialMediaSharingPoints() {
        return socialMediaSharingPoints;
    }

    public void setSocialMediaSharingPoints(Integer socialMediaSharingPoints) {
        this.socialMediaSharingPoints = socialMediaSharingPoints;
    }

    public Integer getReferFriendsPoints() {
        return referFriendsPoints;
    }

    public void setReferFriendsPoints(Integer referFriendsPoints) {
        this.referFriendsPoints = referFriendsPoints;
    }

    public Integer getSpendMoreThanPoints() {
        return spendMoreThanPoints;
    }

    public void setSpendMoreThanPoints(Integer spendMoreThanPoints) {
        this.spendMoreThanPoints = spendMoreThanPoints;
    }
}
