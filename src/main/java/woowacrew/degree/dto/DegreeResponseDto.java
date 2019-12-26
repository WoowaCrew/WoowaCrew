package woowacrew.degree.dto;

public class DegreeResponseDto {
    private Long id;
    private int degreeNumber;

    private DegreeResponseDto() {
    }

    public DegreeResponseDto(Long id, int degreeNumber) {
        this.id = id;
        this.degreeNumber = degreeNumber;
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

    @Override
    public String toString() {
        return "DegreeResponseDto{" +
                "id=" + id +
                ", degreeNumber=" + degreeNumber +
                '}';
    }
}
