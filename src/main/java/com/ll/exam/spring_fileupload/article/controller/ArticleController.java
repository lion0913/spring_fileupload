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
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.server.ResponseStatusException;

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


    @GetMapping("/list")
    public String getList(@RequestParam(required = false) String kwType, @RequestParam(required = false) String kw, Model model) {
        List<Article> articles = articleService.listByCriteria(kwType, kw);

        model.addAttribute("articles", articles);
        return "article/list";
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/write")
    public String showWrite() {
        return "article/write";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/write")
    public String write(@AuthenticationPrincipal MemberContext memberContext, @Valid ArticleForm articleForm, MultipartRequest multipartRequest) {

        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();

        log.debug("fileMap : " + fileMap);
        Article article = articleService.write(memberContext.getId(), articleForm.getSubject(), articleForm.getContent(), articleForm.getHashTagContents());

        ResultData<Map<String, GenFile>> saveFilesRsData = genFileService.saveFiles(article, fileMap);

        log.debug("saveFilesRsData : " + saveFilesRsData);


        String msg = "%d??? ???????????? ?????????????????????.".formatted(article.getId());
        msg = Util.url.encode(msg);
        return "redirect:/article/%d?msg=%s".formatted(article.getId(), msg);
    }

    @GetMapping("/{id}")
    public String showDetail(Model model, @PathVariable Long id) {
        Article article = articleService.getForPrintArticleById(id);


        model.addAttribute("article", article);

        return "article/detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}/modify")
    public String showModify(@AuthenticationPrincipal MemberContext memberContext, Model model, @PathVariable Long id) {
        Article article = articleService.getForPrintArticleById(id);

        if (memberContext.memberIs(article.getAuthor()) == false) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        model.addAttribute("article", article);

        return "article/modify";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{id}/modify")
    public String modify(@AuthenticationPrincipal MemberContext memberContext,
                         Model model, @PathVariable Long id,
                         @Valid ArticleForm articleForm,
                         MultipartRequest multipartRequest,
                         @RequestParam Map<String, String> params) {

        Article article = articleService.getForPrintArticleById(id);

        if (memberContext.memberIs(article.getAuthor()) == false) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }


        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();

        genFileService.deleteFiles(article, params);
        ResultData<Map<String, GenFile>> saveFilesRsData = genFileService.saveFiles(article, fileMap);

        articleService.modify(article, articleForm.getSubject(), articleForm.getContent(), articleForm.getHashTagContents());
        String msg = Util.url.encode("%d??? ???????????? ?????????????????????.".formatted(id));
        return "redirect:/article/%d?msg=%s".formatted(id, msg);
    }

}
