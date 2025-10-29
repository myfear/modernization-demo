package com.example.employee.model;

import com.example.employee.model.enums.ReviewType;
import java.time.LocalDate;
import java.util.Objects;

public class PerformanceReview {
    private final String employeeId;
    private final LocalDate reviewDate;
    private final int rating;
    private final String comments;
    private final double bonusPercentage;
    private final ReviewType reviewType;
    
    public PerformanceReview(String employeeId, LocalDate reviewDate, 
                            int rating, String comments, double bonusPercentage,
                            ReviewType reviewType) {
        this.employeeId = employeeId;
        this.reviewDate = reviewDate;
        this.rating = rating;
        this.comments = comments;
        this.bonusPercentage = bonusPercentage;
        this.reviewType = reviewType;
    }
    
    public String getEmployeeId() { return employeeId; }
    public LocalDate getReviewDate() { return reviewDate; }
    public int getRating() { return rating; }
    public String getComments() { return comments; }
    public double getBonusPercentage() { return bonusPercentage; }
    public ReviewType getReviewType() { return reviewType; }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PerformanceReview that = (PerformanceReview) o;
        return rating == that.rating &&
               Double.compare(that.bonusPercentage, bonusPercentage) == 0 &&
               Objects.equals(employeeId, that.employeeId) &&
               Objects.equals(reviewDate, that.reviewDate) &&
               Objects.equals(comments, that.comments) &&
               reviewType == that.reviewType;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(employeeId, reviewDate, rating, comments, bonusPercentage, reviewType);
    }
}

// Made with Bob
