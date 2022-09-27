package com.imagegallery.model;

import com.imagegallery.util.PathPatternConstraint;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Image {
    @Id
    private String id = UUID.randomUUID().toString();

    @PathPatternConstraint
    private String path;

    @NotEmpty
    @Size(min = 1, max = 200)
    @Pattern(regexp = "^[a-zA-Z0-9_ ]*$")
    private String desc;

    @NotBlank
    @NotEmpty
    @Pattern(regexp = "^((19|2[0-9])[0-9]{2})-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$")
    private String date;

    public Image() {}

    public Image(String path, String desc, String date) {
        this.path = path;
        this.desc = desc;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return Objects.equals(id, image.id) && Objects.equals(path, image.path) && Objects.equals(desc, image.desc) && Objects.equals(date, image.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, path, desc, date);
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", path='" + path + '\'' +
                ", desc='" + desc + '\'' +
                ", date='" + date + '\'' +
                '}';
    }


}
