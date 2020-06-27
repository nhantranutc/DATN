package application.controller.web;

import application.constant.FormatDate;
import application.data.entity.News;
import application.data.service.NewsService;
import application.model.viewmodel.accessary.AccessaryVM;
import application.model.viewmodel.news.HomeNewsVM;
import application.model.viewmodel.news.NewsVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/news")
public class NewsController extends BaseController {
    @Autowired
    private NewsService newsService;

    @GetMapping("")
    public String news(Model model,
                       @Valid @ModelAttribute("accessarytName") AccessaryVM accessarytName,
                       @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                       @RequestParam(name = "size", required = false, defaultValue = "4") Integer size) throws ParseException {
        HomeNewsVM vm = new HomeNewsVM();
        Pageable pageable = new PageRequest(page, size);

        Page<News> newsPage = newsService.getListAllNews(pageable);
        List<NewsVM> newsVMList = new ArrayList<>();
        for(News news : newsPage.getContent()) {
            NewsVM newsVM = new NewsVM();
            newsVM.setNewsId(news.getId());
            newsVM.setTitle(news.getTitle());
            newsVM.setMainImage(news.getMainImage());
            newsVM.setShortDesc(news.getShortDesc());
            newsVM.setAuthor(news.getAuthor());
            newsVM.setCreateDate(FormatDate.formatDate(news.getCreateDate()));

            newsVMList.add(newsVM);
        }

        List<News> hotNewsPage = newsService.getListAllHotNews();
        List<NewsVM> hotNewsVMList = new ArrayList<>();

        for(News news : hotNewsPage) {
            NewsVM newsVM = new NewsVM();
            newsVM.setNewsId(news.getId());
            newsVM.setTitle(news.getTitle());
            newsVM.setMainImage(news.getMainImage());
            newsVM.setShortDesc(news.getShortDesc());
            newsVM.setAuthor(news.getAuthor());
            newsVM.setCreateDate(FormatDate.formatDate(news.getCreateDate()));

            hotNewsVMList.add(newsVM);
        }

        vm.setLayoutHeaderVM(super.getLayoutHeaderVM());
        vm.setNewsVMList(newsVMList);
        vm.setHotNewsVMList(hotNewsVMList);
        vm.setAccessaryTypeVMS(super.getAccessaryTypeVM());

        model.addAttribute("vm", vm);
        model.addAttribute("page", newsPage);
        return "news";
    }

    @GetMapping("detail/{newsId}")
    public String newsDetail (Model model, @PathVariable int newsId,
                              @Valid @ModelAttribute("accessarytName") AccessaryVM accessarytName) throws ParseException {
        HomeNewsVM vm = new HomeNewsVM();

        News news = newsService.findOne(newsId);

        NewsVM newsVM = new NewsVM();

        newsVM.setTitle(news.getTitle());
        newsVM.setContent(news.getContent());
        newsVM.setAuthor(news.getAuthor());
        newsVM.setMainImage(news.getMainImage());
        newsVM.setShortDesc(news.getShortDesc());
        newsVM.setCreateDate(FormatDate.formatDate(news.getCreateDate()));

        List<News> hotNewsPage = newsService.getListAllHotNews();
        List<NewsVM> hotNewsVMList = new ArrayList<>();

        for(News news1 : hotNewsPage) {
            NewsVM newsVM1 = new NewsVM();
            newsVM1.setNewsId(news1.getId());
            newsVM1.setTitle(news1.getTitle());
            newsVM1.setMainImage(news1.getMainImage());
            newsVM1.setShortDesc(news1.getShortDesc());
            newsVM1.setAuthor(news1.getAuthor());
            newsVM1.setCreateDate(FormatDate.formatDate(news.getCreateDate()));

            hotNewsVMList.add(newsVM1);
        }

        vm.setLayoutHeaderVM(super.getLayoutHeaderVM());
        vm.setNewsVM(newsVM);
        vm.setHotNewsVMList(hotNewsVMList);
        vm.setAccessaryTypeVMS(super.getAccessaryTypeVM());

        model.addAttribute("vm", vm);

        return "news-detail";
    }
}
