package woowacrew.degree.dto;

public class DegreeWithUserCountResponseDto {
    private Long id;
    private int number;
    private int numberOfUser;

    private DegreeWithUserCountResponseDto() {
    }

    public DegreeWithUserCountResponseDto(Long id, int number, int numberOfUser) {
        this.id = id;
        this.number = number;
        this.numberOfUser = numberOfUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumberOfUser() {
        return numberOfUser;
    }

    public void setNumberOfUser(int numberOfUser) {
        this.numberOfUser = numberOfUser;
    }
}
