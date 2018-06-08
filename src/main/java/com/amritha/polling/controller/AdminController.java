package com.amritha.polling.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.amritha.polling.dao.PollCategoryDao;
import com.amritha.polling.dao.PollQuestionsDao;
import com.amritha.polling.dao.UserDao;
import com.amritha.polling.model.PollCategory;
import com.amritha.polling.model.PollQuestions;
import com.amritha.polling.model.User;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	PollCategoryDao pcDao;
	@Autowired
	PollQuestionsDao pqDao;
	@Autowired
	UserDao userDao;
	@RequestMapping("/dispajax")
		public String categorydetails(Model model) {
			
			List<PollCategory> pc=pcDao.listcategories();
			model.addAttribute("categories", pc);
			model.addAttribute("pollcategory", new PollCategory());
			return "adminpolls";
	}
	
	@RequestMapping("/editusers")
	public String editusers() {
		return "listusers";
	}
	@RequestMapping("/setaspollmaster/{id}")
	public String setaspm(@PathVariable("id") int id)
	{
		userDao.setrole(id);
		return "listusers";
	}
	
	@RequestMapping("/pollmasters/{id}")
	public String pollmasters(@PathVariable("id") int id,Model model) {
		
		PollCategory pc=pcDao.getcategorybyid(id);
	List<User> pmforpc=pc.getPollmasters();
	if(pmforpc!=null) {
		model.addAttribute("pmforpc",pmforpc);
	}
	else
	{
model.addAttribute("pmforpc",new ArrayList<User>());		
	}
	model.addAttribute("id", id);
	List<User> pollmas=userDao.getPollMasters();
	model.addAttribute("pollmas", pollmas);
	return "pollmastersforpc";
}
	
	@RequestMapping("/addpmforpc/{id}/{pmid}")
     public String addpmforpc(@PathVariable("id") int id,@PathVariable("pmid") int pmid,Model model) {
		User user=userDao.getUserById(pmid);
		PollCategory pc=pcDao.getcategorybyid(id);
		List<User> pmforpc=pc.getPollmasters();
		//boolean is=pmforpc.contains(user);
		for(User pm:pmforpc) {
			if(pm.getId()==pmid) {
				System.out.println("element already present");
				model.addAttribute("pmforpc",pmforpc);
				model.addAttribute("id", id);
				List<User> pollmas=userDao.getPollMasters();
				model.addAttribute("pollmas", pollmas);
				return "pollmastersforpc";
		}}
		
		pmforpc.add(user);
		pc.setPollmasters(pmforpc);
		pcDao.savecategory(pc);
		
		
		model.addAttribute("pmforpc",pmforpc);
		model.addAttribute("id", id);
		List<User> pollmas=userDao.getPollMasters();
		model.addAttribute("pollmas", pollmas);
		return "pollmastersforpc";
		
	}
	

	@RequestMapping("/deletepmforpc/{id}/{pmid}")
    public String deletepmforpc(@PathVariable("id") int id,@PathVariable("pmid") int pmid,Model model) {
	System.out.println("happy deleting");
		User user=userDao.getUserById(pmid);
		PollCategory pc=pcDao.getcategorybyid(id);
		List<User> pmforpc=new ArrayList<User>();
		pmforpc=pc.getPollmasters();
		
		for(User pm:pmforpc) {
			if(pm.equals(user)) {
				System.out.println(pmforpc.remove(user));
				break;
			}
		}
		//System.out.println("index is"+pmforpc.indexOf(user));
		//System.out.println(pmforpc.remove(user));
		//pmforpc.remove(user);
		pc.setPollmasters(pmforpc);
		pcDao.savecategory(pc);
		
		
		
		model.addAttribute("pmforpc",pmforpc);
		model.addAttribute("id", id);
		List<User> pollmas=userDao.getPollMasters();
		model.addAttribute("pollmas", pollmas);
		return "pollmastersforpc";
		
	}
	@RequestMapping("/listusers")
	public @ResponseBody List<User> listusers(Model model)
	{
		List<User> userlist=userDao.listusers();
		//model.addAttribute("users",userlist);
		return userlist;
	}
	@RequestMapping("/listquestions/{id}")
	public String listques(@PathVariable("id") int id, Model model) {
		model.addAttribute("pq", pqDao.listquestions(id));
		//model.addAttribute("que",new PollQuestions());
		
		model.addAttribute("id", id);
		return "listquestions";

}
	
	@RequestMapping("/addcategory")
	public String addcategory(Model model) {
		model.addAttribute("pollcategory",new PollCategory());
		return "categoryadd";
	}
	@RequestMapping("/savecategory")
		public  String addcategory(@ModelAttribute("pollcategory") PollCategory pollcategory,BindingResult bindingresult,Model model) {
		System.out.println("hello");
		 if (bindingresult.hasErrors()) {
			 System.out.println("hiihello");
	         return "categoryadd";
	      }
		pcDao.savecategory(pollcategory);
		List<PollCategory> pc=pcDao.listcategories();
		model.addAttribute("categories", pc);
		String msg="hello";
		model.addAttribute("pollcategory", new PollCategory());
		return "adminpolls";
	}
	
	@RequestMapping("/editcategory/{id}")
	public  String editcategory(@PathVariable("id") int id,Model model) {
	model.addAttribute("id", id);
	model.addAttribute("pq", pqDao.listquestions(id));
		return "listquestions";
	}
	/*@RequestMapping("/addquestions/{id}")
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
	}*/
		
		@RequestMapping("/deletecategory/{id}")
	public String deletecategory(@PathVariable("id") int id,Model model) {
		
		Boolean b=pcDao.deletecategory(id);
		List<PollCategory> pc=pcDao.listcategories();
		model.addAttribute("categories", pc);
		model.addAttribute("pollcategory", new PollCategory());
		return "adminpolls";
		
	}
}