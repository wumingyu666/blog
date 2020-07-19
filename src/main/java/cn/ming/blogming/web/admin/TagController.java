package cn.ming.blogming.web.admin;

import cn.ming.blogming.Service.TagService;
import cn.ming.blogming.Service.TypeService;
import cn.ming.blogming.domain.Tag;
import cn.ming.blogming.domain.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TagController {
    @Autowired
    private TagService tagService;
    @GetMapping("/tags")
    public String list(@PageableDefault(size = 5,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable,
                       Model model){

        model.addAttribute("page", tagService.listTag(pageable));
        return "admin/tags";
    }
    @GetMapping("/tags/input")
    public String input(Model model){
        //后端的校验
        model.addAttribute("tag",new Tag());
        return "admin/tags-input";
    }
    @PostMapping("/tags")
    public String postTag(@Valid Tag tag, BindingResult result, RedirectAttributes redirectAttributes){
        Tag tag2 = tagService.findByName(tag.getName());
        if(tag2!=null){
            result.rejectValue("name","nameError","不能添加重复的标签");
        }
        if(result.hasErrors()){
            return "admin/tags-input";
        }
        Tag tag1 = tagService.saveTag(tag);

        if(tag1==null){
            redirectAttributes.addFlashAttribute("messaage","很抱歉！添加失败！");
        }else{
            redirectAttributes.addFlashAttribute("message","恭喜你！添加成功！");
        }
        return "redirect:/admin/tags";
    }
    @GetMapping("/tags/{id}/input")
    public String edit(@PathVariable Long id, Model model){
        Tag tag = tagService.getTag(id);
        model.addAttribute("tag",tag);
        return "admin/tags-input";
    }
    @PostMapping("/tags/{id}")
    public String editpostTag(@Valid Tag tag, BindingResult result, @PathVariable Long id, RedirectAttributes redirectAttributes){
        Tag tag2 = tagService.findByName(tag.getName());
        if(tag2!=null){
            result.rejectValue("name","nameError","不能添加重复的标签");
        }
        if(result.hasErrors()){
            return "admin/tags-input";
        }
        Tag tag1 = tagService.updateTag(id,tag);

        if(tag1==null){
            redirectAttributes.addFlashAttribute("messaage","很抱歉！更新失败！");
        }else{
            redirectAttributes.addFlashAttribute("message","恭喜你！更新成功！");
        }
        return "redirect:/admin/tags";
    }
    @GetMapping("/tags/{id}/delete")
    public String deleteTag(@PathVariable Long id,RedirectAttributes redirectAttributes){
        tagService.deleteTag(id);
        redirectAttributes.addFlashAttribute("message","删除成功！");
        return "redirect:/admin/tags";
    }
}
