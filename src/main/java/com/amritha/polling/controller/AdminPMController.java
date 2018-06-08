package com.amritha.polling.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.amritha.polling.dao.PollCategoryDao;
import com.amritha.polling.dao.PollQuestionsDao;
import com.amritha.polling.dao.UserDao;
import com.amritha.polling.model.PollCategory;
import com.amritha.polling.model.PollQuestions;

@Controller
@RequestMapping("/ap")
public class AdminPMController {

	
	@Autowired
	PollCategoryDao pcDao;
	@Autowired
	PollQuestionsDao pqDao;
	@Autowired
	UserDao userDao;

	
	@RequestMapping("/listquestions/{id}")
	public String listques(@PathVariable("id") int id, Model model) {
		model.addAttribute("pq", pqDao.listquestions(id));
		//model.addAttribute("que",new PollQuestions());
		
		model.addAttribute("id", id);
		return "listquestions";

}
	

	
	@RequestMapping("/editcategory/{id}")
	public  String editcategory(@PathVariable("id") int id,Model model) {
	model.addAttribute("id", id);
	model.addAttribute("pq", pqDao.listquestions(id));
		return "listquestions";
	}
	@RequestMapping("/addquestions/{id}")
	public String addquestions(@PathVariable("id") int id,Model model) {
		model.addAttribute("cid",id);
		PollCategory pc=pcDao.getcategorybyid(id);
		model.addAttribute("pcname",pc.getPollcategory());
		model.addAttribute("pollcategorylist", pcDao.listcategories());
		model.addAttribute("question", new PollQuestions());
		return "questionadd";
	}
	
	@RequestMapping("/savequestion/{cid}")
	public String savequestion(@ModelAttribute("question") PollQuestions question,@PathVariable("cid") int cid,Model model) {
		//System.out.println("error.!");
		
		pqDao.savequestion(question);
		model.addAttribute("id", cid);
		model.addAttribute("pq", pqDao.listquestions(cid));
		return "listquestions";
	}
	@RequestMapping("/editquestion/{id}")
	public String editquestion(@PathVariable("id") int id,Model model) {
		PollQuestions pollquestion=pqDao.getquestionbyid(id);
		model.addAttribute("cid",pollquestion.getPollcategory().getId());
		model.addAttribute("pcname",pollquestion.getPollcategory().getPollcategory());
		model.addAttribute("que", pollquestion);
		List<PollCategory> pc=pcDao.listcategories();
		model.addAttribute("categories", pc);
		return "editquestion";
	}
	@RequestMapping("/confeditque/{cid}")
	public String confirmeditque(@PathVariable("cid") int cid,@ModelAttribute("que") PollQuestions que,BindingResult bindingresult,Model model)
	{
		if (bindingresult.hasErrors()) {
			 System.out.println("hiihello");
			 System.out.println(bindingresult.getAllErrors());
	         return "editquestion";
	      }
		pqDao.savequestion(que);
		model.addAttribute("pq", pqDao.listquestions(cid));
		model.addAttribute("id",cid);
		return "listquestions";
		
	}
	@RequestMapping("/deletequestion/{id}")
	public String deletequestion(@ModelAttribute("question") PollQuestions question,@PathVariable("id") int id,Model model) {
	      
		PollQuestions pollquestion=pqDao.getquestionbyid(id);
		int cid=pollquestion.getPollcategory().getId();
		model.addAttribute("id",cid);
		pqDao.deletequestion(question,id);
		model.addAttribute("pq", pqDao.listquestions(cid));
		return "listquestions";
	}
		
}