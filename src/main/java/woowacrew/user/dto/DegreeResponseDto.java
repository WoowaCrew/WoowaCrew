package woowacrew.user.dto;

public class DegreeResponseDto {
    private Long id;
    private int number;

    private DegreeResponseDto() {
    }

    public DegreeResponseDto(Long id, int number) {
        this.id = id;
        this.number = number;
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
}
