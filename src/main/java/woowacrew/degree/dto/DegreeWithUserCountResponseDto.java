package woowacrew.degree.dto;

public class DegreeWithUserCountResponseDto {
    private Long id;
    private int degreeNumber;
    private int userCount;

    private DegreeWithUserCountResponseDto() {
    }

    public DegreeWithUserCountResponseDto(Long id, int degreeNumber, int userCount) {
        this.id = id;
        this.degreeNumber = degreeNumber;
        this.userCount = userCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getDegreeNumber() {
        return degreeNumber;
    }

    public void setDegreeNumber(int degreeNumber) {
        this.degreeNumber = degreeNumber;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    @Override
    public String toString() {
        return "DegreeWithUserCountResponseDto{" +
                "id=" + id +
                ", degreeNumber=" + degreeNumber +
                ", userCount=" + userCount +
                '}';
    }
}
