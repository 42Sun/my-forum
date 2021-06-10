package com.sundingyi.forum.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO<T> {
    private List<T> data;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showLastPage;
    private Integer currentPage;
    private List<Integer> pages = new ArrayList<>();
    private Integer totalPage;
    
    public void setPagination(Integer totalCount, Integer page, Integer size) {
        
        int totalPage;
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        this.totalPage = totalPage;
        if (page < 0 || page > totalPage) {
            page = 1;
        }
        this.currentPage = page;
        for (int i = 3; i > 0; i--) {
            if (page - i >= 1) {
                pages.add(page - i);
            }
        }
        pages.add(page);
        for (int i = 1; i < 4; i++) {
            if (page + i <= totalPage) {
                pages.add(page + i);
            }
        }
        this.showPrevious = page != 1; // 展示上一页逻辑
        this.showNext = !page.equals(totalPage); // 展示下一页逻辑
        showFirstPage = !pages.contains(1); // 展示第一页逻辑
        showLastPage = !pages.contains(totalPage); // 展示最后一页逻辑
    }
}
