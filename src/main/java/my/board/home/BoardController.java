package my.board.home;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import my.board.object.ContentService;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Inject
	private ContentService service = new ContentService(1, 10);
	
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String boardHome() {
		if( service.checkConnection() ) {
			return "board/notConnection";
		} else {
			return "/board/List";
		}
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String boardList(HttpServletRequest request, Model model) {
		String view = "/board/ContentList";
		
		System.out.println("[Connection]");
		service.checkConnection();
		System.out.println("[Connection]");
		
		// parameter is no contain "page"
		if( request.getParameter("page") == null ) {
			model.addAttribute("contents", service.getList(1));
		} else {
			int pageNumber = Integer.parseInt(request.getParameter("page"));
			model.addAttribute("contents", service.getList(pageNumber));
		}
		return view;
	}
	
}
