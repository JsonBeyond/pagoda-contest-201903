package com.pagoda.hdtt.controller;

import com.jfinal.plugin.activerecord.Page;
import com.pagoda.hdtt.aotogen.MsgTemplate;
import com.pagoda.hdtt.aotogen.RepositoryContent;
import com.pagoda.hdtt.common.exception.ServiceException;
import com.pagoda.hdtt.common.util.BeanUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName RepositoryContentController
 * @Description 知识库
 * @Author Alex
 * @CreateDate 1/3/2020 5:24 PM
 * @Version 1.0
 */
public class RepositoryContentController extends BaseAPIController {

    public void insert() {
        RepositoryContent repositoryContent = getJsonModel(RepositoryContent.class);
        boolean result = repositoryContent.save();
        successResponse(result);
    }

    public void delete(Integer id) {
        RepositoryContent repositoryContent = RepositoryContent.dao.findById(id);
        if (BeanUtil.checkIsEmpty(repositoryContent)) {
            throw new ServiceException("未找到对应知识库!");
        }
        boolean result = repositoryContent.delete();
        successResponse(result);
    }

    public void update() {
        RepositoryContent repositoryContent = getJsonModel(RepositoryContent.class);
        boolean result = repositoryContent.update();
        successResponse(result);
    }

    public void list(Integer page, Integer pageSize, String question) {
        String sqlExceptSelect = "from  repository_content where 1=1 ";
        List<String> params = new ArrayList<>();
        if (BeanUtil.checkIsNotEmpty(question)) {
            sqlExceptSelect += " and question like ?";
            params.add("%" + question + "%");
        }
        sqlExceptSelect += " order by create_date desc";

        Page<MsgTemplate> paginate = MsgTemplate.dao.paginate(page, pageSize, "select *", sqlExceptSelect, params.toArray());
        successResponse(paginate);
    }

    public void get(Integer id) {
        RepositoryContent repositoryContent = RepositoryContent.dao.findById(id);
        if(BeanUtil.checkIsEmpty(repositoryContent)){
            throw new ServiceException("未找到对应知识库!");
        }
        successResponse(repositoryContent);

    }
}
