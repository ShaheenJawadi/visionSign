package apiUtils;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class UploadImage {
    private Cloudinary cloudinary;

    public UploadImage( ) {
        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dvs84sv5n",
                "api_key", "866199197682313",
                "api_secret", "b770BiA6DtLdW_XLAmyzP4kQKwY"));
    }

    public String uploadImage(File imageFile) throws IOException {
        Map<?, ?> uploadResult = cloudinary.uploader().upload(imageFile, ObjectUtils.emptyMap());
        return uploadResult.get("secure_url").toString();
    }

}
