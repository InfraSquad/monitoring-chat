package com.example.demo.controller;

import com.example.demo.model.Note;
import com.example.demo.model.UrlStatus;
import com.example.demo.repository.NoteRepository;
import com.example.demo.repository.UrlStatusRepository;
import com.example.demo.service.HealthCheckService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class HomeController {
    private final UrlStatusRepository repo;
    private final HealthCheckService healthService;
    private final NoteRepository noteRepo;

    public HomeController(UrlStatusRepository repo, HealthCheckService healthService,  NoteRepository noteRepo) {
        this.repo = repo;
        this.healthService = healthService;
        this.noteRepo = noteRepo;
    }

    @GetMapping("/architecture")
    public String showArchitecturePage(Model model) {
        model.addAttribute("view", "view/architecture");
        return "layout"; // Load layout.html
    }

    @GetMapping("/users")
    public String showUsers(Model model) {
        model.addAttribute("view", "view/users");
        return "layout"; // Load layout.html
    }

    @GetMapping("/settings")
    public String showSettings(Model model) {
        model.addAttribute("view", "view/settings");
        return "layout"; // Load layout.html
    }

    @GetMapping("/note")
    public String showNotepad(@RequestParam(required = false) Long id, Model model) {
        Note currentNote = (id != null) ? noteRepo.findById(id).get() : noteRepo.findTopByOrderByLastUpdatedDesc().orElseGet(() -> new Note("", ""));
        List<Note> allNotes = noteRepo.findAll();
        model.addAttribute("note", currentNote);
        model.addAttribute("noteList", allNotes);
        model.addAttribute("activeId", currentNote.getId());
        model.addAttribute("view", "view/note");
        return "layout"; // Load layout.html
    }

    @GetMapping("/note/new")
    public String createNewNote(RedirectAttributes redirect) {
        Note note = new Note();
        note.setTitle("Ghi chú mới");
        note.setContent("");
        note = noteRepo.save(note);
        return "redirect:/note?id=" + note.getId();
    }

    @GetMapping("/note/delete/{id}")
    public String deleteNote(@PathVariable Long id, RedirectAttributes redirect) {
        noteRepo.deleteById(id);
        return "redirect:/note";
    }

    @GetMapping("/")
    public String redirectToDashboard() {
        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model,
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "6") int size) {
//        healthService.checkAll();
        Page<UrlStatus> urlPage = repo.findAll(PageRequest.of(page, size));
        model.addAttribute("urlPage", urlPage);
        model.addAttribute("view", "view/dashboard");
        return "layout"; // Load layout.html
    }

    @GetMapping("/raw")
    public String showRaw(Model model,
                          @RequestParam(defaultValue = "0") int page,
                          @RequestParam(defaultValue = "6") int size) {
        Page<UrlStatus> urlPage = repo.findAll(PageRequest.of(page, size));
        model.addAttribute("urlPage", urlPage);
        model.addAttribute("view", "view/raw");
        return "layout"; // Load layout.html
    }
}
