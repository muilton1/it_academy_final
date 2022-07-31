package it.academy.user_service.mappers;


import it.academy.user_service.dto.PageContent;
import org.springframework.data.domain.Page;

public class PageContentMapper<T> {
    public PageContent<T> map(Page<T> dtoPage) {
        return new PageContent<>(dtoPage.getNumber(),
                dtoPage.getSize(),
                dtoPage.getTotalPages(),
                dtoPage.getTotalElements(),
                dtoPage.isFirst(),
                dtoPage.getNumberOfElements(),
                dtoPage.isLast(),
                dtoPage.getContent());
    }
}
