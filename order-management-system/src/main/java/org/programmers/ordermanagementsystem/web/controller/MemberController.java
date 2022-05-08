package org.programmers.ordermanagementsystem.web.controller;

import lombok.RequiredArgsConstructor;
import org.programmers.ordermanagementsystem.dto.MemberCreateForm;
import org.programmers.ordermanagementsystem.dto.MemberDto;
import org.programmers.ordermanagementsystem.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members")
    public String memberList(Model model) {
        List<MemberDto> members = memberService.getAllMembers().stream().map(MemberDto::from).toList();
        model.addAttribute("members", members);

        return "/member/memberList";
    }

    @GetMapping("/members/new")
    public String newMember(Model model) {
        model.addAttribute("member", new MemberCreateForm());

        return "/member/newMember";
    }

    @PostMapping("/members/new")
    public String registerMember(@ModelAttribute("member") MemberCreateForm form, RedirectAttributes redirectAttributes) {

        var member = memberService.registerMember(form);
        redirectAttributes.addAttribute("id", member.getId());

        return "redirect:/members";
    }

    @PostMapping("/members/{id}/delete")
    public String deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);

        return "redirect:/members";
    }
}
