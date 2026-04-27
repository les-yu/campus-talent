package com.campus.talent.dto;

import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.util.List;

/**
 * 统一分页返回格式
 */
@Data
public class PageResult<T> {
    private List<T> list;      // 当前页数据
    private Long total;        // 总记录数
    private Integer pages;     // 总页数
    private Integer pageNum;   // 当前页码
    private Integer pageSize;  // 每页条数

    public static <T> PageResult<T> of(PageInfo<T> pageInfo) {
        PageResult<T> r = new PageResult<>();
        r.list     = pageInfo.getList();
        r.total    = pageInfo.getTotal();
        r.pages    = pageInfo.getPages();
        r.pageNum  = pageInfo.getPageNum();
        r.pageSize = pageInfo.getPageSize();
        return r;
    }
}