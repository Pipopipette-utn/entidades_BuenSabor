package com.example.buensaborback.domain.dto;

import org.springframework.web.multipart.MultipartFile;

public class ImageModel {
    private String name;
    private MultipartFile file;

    public ImageModel() {
    }

    public String getName() {
        return this.name;
    }

    public MultipartFile getFile() {
        return this.file;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setFile(final MultipartFile file) {
        this.file = file;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof ImageModel)) {
            return false;
        } else {
            ImageModel other = (ImageModel)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$name = this.getName();
                Object other$name = other.getName();
                if (this$name == null) {
                    if (other$name != null) {
                        return false;
                    }
                } else if (!this$name.equals(other$name)) {
                    return false;
                }

                Object this$file = this.getFile();
                Object other$file = other.getFile();
                if (this$file == null) {
                    if (other$file != null) {
                        return false;
                    }
                } else if (!this$file.equals(other$file)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ImageModel;
    }

    public int hashCode() {
        int PRIME = true;
        int result = 1;
        Object $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        Object $file = this.getFile();
        result = result * 59 + ($file == null ? 43 : $file.hashCode());
        return result;
    }

    public String toString() {
        String var10000 = this.getName();
        return "ImageModel(name=" + var10000 + ", file=" + String.valueOf(this.getFile()) + ")";
    }
}