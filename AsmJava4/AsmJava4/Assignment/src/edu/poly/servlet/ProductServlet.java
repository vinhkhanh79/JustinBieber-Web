package edu.poly.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.poly.dao.CommentDAO;
import edu.poly.dao.FavoriteDAO;
import edu.poly.dao.VideoDAO;
import edu.poly.entity.Comment;
import edu.poly.entity.Favorite;
import edu.poly.entity.Users;
import edu.poly.entity.Video;

/**
 * Servlet implementation class ProductServlet
 */
@WebServlet({
	"/watch-product/index",
	"/watch-product/id/*",
	"/watch-product/category",
	"/watch-product/detail",
	"/watch-product/search",
	"/watch-product/comment"
})
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	VideoDAO vdDao = new VideoDAO();
	List<Object> obList = new ArrayList<>();
	List<String> category = vdDao.findCategory();
	CommentDAO cmtDao = new CommentDAO();
	List<Comment> listCmt = new ArrayList<>();
	String idVideo = "";
    public ProductServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String forward = "/views/user/user.jsp";
		String uri = request.getRequestURI();
		if(uri.contains("index")) {
			showProductIndex(request, response);
			forward = "/views/user/user.jsp";
		}
		if(uri.contains("id")) {
			watchProductIndex(request, response);
			forward = "/views/user/user.jsp";
		}
		if(uri.contains("category")) {
			watchCategoryProduct(request, response);
			forward = "/views/user/user.jsp";
		}
		if(uri.contains("detail")) {
			watchDetailProduct(request, response);
			forward = "/views/user/user.jsp";
		}
		request.getRequestDispatcher(forward).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String forward = "/views/user/user.jsp";
		String uri = request.getRequestURI();
		if(uri.contains("index")) {
			showProductIndex(request, response);
		}
		if(uri.contains("search")) {
			showProductSearch(request, response);
		}if(uri.contains("comment")) {
			watchCmtProduct(request, response);
		}
		request.getRequestDispatcher(forward).forward(request, response);
	}

	public void showProductIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		obList.clear();
		request.getSession().removeAttribute("detail");
		Users user = (Users) request.getSession().getAttribute("user");
		if(user == null) {
			for(int i=0;i<category.size();i++) {
				List<Video> vd = vdDao.findByCategoryShowIndex(category.get(i));
				List<Object> list = new ArrayList<>();
				for(Video v:vd) {
					Object[] ob = {v, false};
					list.add(ob);
				}
				Object[] ob = {category.get(i), list};
				obList.add(ob);
			}
		}
		else {
			FavoriteDAO fDao = new FavoriteDAO();
			List<Favorite> listFa = fDao.findByUser(user.getId());
			for(int i=0;i<category.size();i++) {
				List<Object> list = new ArrayList<>();
				List<Video> vd = vdDao.findByCategoryShowIndex(category.get(i));
				for(Video v:vd) {
					Object[] ob = {v, false};
					for(Favorite f:listFa) {
						if(v.getId().equals(f.getVideo().getId())) {
							ob[1] = true;
							break;
						}
					}
					list.add(ob);
				}
				Object[] ob = {category.get(i), list};
				obList.add(ob);
			}
		}
		
		request.getSession().setAttribute("categorys", category);
		request.getSession().setAttribute("map", obList);
	}
	
	public void showProductSearch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		List<Object> list = new ArrayList<>();
		Users user = (Users) request.getSession().getAttribute("user");
		String search = request.getParameter("search");
		if(search == "") {
			showProductIndex(request, response);
		}
		else {
			if(user == null) {
				List<Video> vd = vdDao.findByTitleGenres(search);
				for(Video v:vd) {
					Object[] ob = {v, false};
					list.add(ob);
				}
			}
			else {
				FavoriteDAO fDao = new FavoriteDAO();
				List<Favorite> listFa = fDao.findByUser(user.getId());
				List<Video> vd = vdDao.findByTitleGenres(search);
				for(Video v:vd) {
					Object[] ob = {v, false};
					for(Favorite fa:listFa) {
						if(v.getId().equals(fa.getVideo().getId())) {
							ob[1] = true;
							break;
						}
					}
					list.add(ob);
				}
			}
		}
		
		request.setAttribute("search", list);
	}
	
	public void watchProductIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Video vd = vdDao.findByPoster(request.getParameter("id"));
		vd.setViews(vd.getViews()+1);
		vdDao.update(vd);
		request.setAttribute("watchVideoId", request.getParameter("id"));
	}
	
	public void watchDetailProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Users user = (Users) request.getSession().getAttribute("user");
		idVideo = request.getParameter("id");
		Video detail = vdDao.findByPoster(idVideo);
		listCmt = cmtDao.findByVideo(detail.getId());
		Object[] ob = {detail, false};
		if(user != null) {
			FavoriteDAO fDao = new FavoriteDAO();
			List<Favorite> listFa = fDao.findByUser(user.getId());
			for(Favorite f:listFa) {
				if(detail.getId().equals(f.getVideo().getId())) {
					ob[1] = true;
					break;
				}
			}
		}
		request.getSession().setAttribute("detail", ob);
		request.setAttribute("cmtList", listCmt);
	}
	
	public void watchCmtProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Users user = (Users) request.getSession().getAttribute("user");
		if(request.getParameter("cmt")!=null) {
			Comment cmt = new Comment();
			cmt.setUser(user);
			cmt.setVideo(vdDao.findByPoster(idVideo));
			cmt.setCmtValue(request.getParameter("cmt"));
			cmt.setCmtDate(new Date());
			cmtDao.create(cmt);
			listCmt = cmtDao.findByVideo(vdDao.findByPoster(idVideo).getId());
		}
		request.setAttribute("cmtList", listCmt);
	}
	
	public void watchCategoryProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		List<Video> vd = vdDao.findByCategory(request.getParameter("cate"));
		Users user = (Users) request.getSession().getAttribute("user");
		List<Object> list = new ArrayList<>();
		if(user == null) {
			for(Video v:vd) {
				Object[] ob = {v, false};
				list.add(ob);
			}
		}
		else {
			FavoriteDAO fDao = new FavoriteDAO();
			List<Favorite> listFa = fDao.findByUser(user.getId());
			for(Video v:vd) {
				Object[] ob = {v, false};
				for(Favorite f:listFa) {
					if(v.getId().equals(f.getVideo().getId())) {
						ob[1] = true;
						break;
					}
				}
				list.add(ob);
			}
		}
		Object[] object = {request.getParameter("cate"), list};
		obList.clear();
		obList.add(object);
	}
}
