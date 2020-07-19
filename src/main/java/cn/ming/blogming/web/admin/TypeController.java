package cn.ming.blogming.web.admin;

import cn.ming.blogming.Service.TypeService;
import cn.ming.blogming.domain.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TypeController {
    @Autowired
    private TypeService typeService;
    @GetMapping("/types")
    public String list(@PageableDefault(size = 5,sort = {"id"},direction = Sort.Direction.DESC)Pageable pageable,
                       Model model){

        model.addAttribute("page", typeService.listType(pageable));
        return "admin/types";
    }
    @GetMapping("/types/input")
    public String input(Model model){
        //后端的校验
        model.addAttribute("type",new Type());
        return "admin/types-input";
    }
    @PostMapping("/types")
    public String postType(@Valid Type type, BindingResult result, RedirectAttributes redirectAttributes){
        Type type2 = typeService.findByName(type.getName());
        if(type2!=null){
            result.rejectValue("name","nameError","不能添加重复的分类");
        }
        if(result.hasErrors()){
            return "admin/types-input";
        }
        Type type1 = typeService.saveType(type);

        if(type1==null){
            redirectAttributes.addFlashAttribute("messaage","很抱歉！添加失败！");
        }else{
            redirectAttributes.addFlashAttribute("message","恭喜你！添加成功！");
        }
        return "redirect:/admin/types";
    }
    @GetMapping("/types/{id}/input")
    public String edit(@PathVariable Long id,Model model){
        Type type = typeService.getType(id);
        model.addAttribute("type",type);
        return "admin/types-input";
    }
    @PostMapping("/types/{id}")
    public String editpostType(@Valid Type type, BindingResult result, @PathVariable Long id, RedirectAttributes redirectAttributes){
        Type type2 = typeService.findByName(type.getName());
        if(type2!=null){
            result.rejectValue("name","nameError","不能添加重复的分类");
        }
        if(result.hasErrors()){
            return "admin/types-input";
        }
        Type type1 = typeService.updateType(id,type);

        if(type1==null){
            redirectAttributes.addFlashAttribute("messaage","很抱歉！更新失败！");
        }else{
            redirectAttributes.addFlashAttribute("message","恭喜你！更新成功！");
        }
        return "redirect:/admin/types";
    }
    @GetMapping("/types/{id}/delete")
    public String deleteType(@PathVariable Long id,RedirectAttributes redirectAttributes){
        typeService.deleteType(id);
        redirectAttributes.addFlashAttribute("message","删除成功！");
        return "redirect:/admin/types";
    }
}
