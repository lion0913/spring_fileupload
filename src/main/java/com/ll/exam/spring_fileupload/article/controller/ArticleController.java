package com.ll.exam.spring_fileupload.article.controller;

import com.ll.exam.spring_fileupload.article.dto.ArticleForm;
import com.ll.exam.spring_fileupload.article.entity.Article;
import com.ll.exam.spring_fileupload.article.service.ArticleService;
import com.ll.exam.spring_fileupload.base.dto.ResultData;
import com.ll.exam.spring_fileupload.fileUpload.entity.GenFile;
import com.ll.exam.spring_fileupload.fileUpload.service.GenFileService;
import com.ll.exam.spring_fileupload.security.dto.MemberContext;

import com.ll.exam.spring_fileupload.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/article")
@Slf4j
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    private final GenFileService genFileService;


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/write")
    public String showWrite() {
        return "article/write";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/write")
    @ResponseBody
    public String write(@AuthenticationPrincipal MemberContext memberContext, @Valid ArticleForm articleForm, MultipartRequest multipartRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "article/write";
        }

        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();

        log.debug("fileMap : " + fileMap);
        Article article = articleService.write(memberContext.getId(), articleForm.getSubject(), articleForm.getContent());

        ResultData<Map<String, GenFile>> saveFilesRsData = genFileService.saveFiles(article, fileMap);

        log.debug("saveFilesRsData : " + saveFilesRsData);


        String msg = "%d번 게시물이 작성되었습니다.".formatted(article.getId());
        msg = Util.url.encode(msg);
        return "redirect:/article/%d?msg=%s".formatted(article.getId(), msg);
    }

    @GetMapping("/{id}")
    public String showDetail(Model model, @PathVariable Long id) {
        Article article = articleService.getArticleById(id);
        List<String> genFileList = genFileService.imgPathByRelId(id);


        model.addAttribute("article", article);
        model.addAttribute("pathList", genFileList);

        return "article/detail";
    }

    @GetMapping("/{id}/json/forDebug")
    @ResponseBody
    public Article showDetailJson(Model model, @PathVariable Long id) {
        return articleService.getForPrintArticleById(id);
    }

}
