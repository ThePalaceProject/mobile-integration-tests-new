package models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CatalogBookModel {
    private String imageTitle;
    private String title;
    private String author;
    private String bookType;
}
