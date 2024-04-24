package edu.poly.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.DateTimeConverter;

import edu.poly.dao.CommentDAO;
import edu.poly.dao.FavoriteDAO;
import edu.poly.dao.ShareDAO;
import edu.poly.dao.UserDAO;
import edu.poly.dao.VideoDAO;
import edu.poly.entity.Users;
import edu.poly.entity.Video;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender.Size;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet({
	"/admin/index",
	"/admin/index/show-all-account",
	"/admin/index/short-account",
	"/admin/index/show-all-product",
	"/admin/index/short-product",
	"/admin/product",
	"/admin/product/add",
	"/admin/product/update",
	"/admin/product/remove",
	"/admin/product/new",
	"/admin/account",
	"/admin/account/add",
	"/admin/account/update",
	"/admin/account/remove",
	"/admin/account/new"
})
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	VideoDAO vdDao = new VideoDAO();
	FavoriteDAO faDao = new FavoriteDAO();
	CommentDAO cmtDao = new CommentDAO();
	ShareDAO shareDao = new ShareDAO();
	UserDAO usDao = new UserDAO();
	List<Video> list = vdDao.findAllAdmin();
	List<Users> accList = usDao.findAll();
	int likes=0,cmts=0,shares=0;
	long highestLike=0,highestCmt=0,highestShare=0;
    public AdminServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String uri = request.getRequestURI();
		getProductIndex(request, response);
		getAccountIndex(request, response);
		getInteractIndex(request, response);
		if(uri.contains("/index/show-all-product")) {
			getProductAll(request, response);
		}
		if(uri.contains("/index/short-product")) {
			request.setAttribute("showProduct", true);
		}
		if(uri.contains("/index/show-all-account")) {
			getAccountAll(request, response);
		}
		if(uri.contains("/index/short-account")) {
			request.setAttribute("showAccount", true);
		}
		if(uri.contains("/admin/account")) {
			Users user = new Users();
			user = usDao.findById(request.getParameter("id"));
			request.setAttribute("us", user);
			request.setAttribute("showAccount", true);
		}
		if(uri.contains("/admin/product")) {
			Video video = new Video();
			video = vdDao.findById(request.getParameter("id"));
			request.setAttribute("vd", video);
			request.setAttribute("showProduct", true);
		}
		request.getRequestDispatcher("/views/admin/admin.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String uri = request.getRequestURI();
		if(uri.contains("/account/add")) {
			addAccount(request, response);
		}
		if(uri.contains("/account/update")) {
			updateAccount(request, response);
		}
		if(uri.contains("/account/remove")) {
			removeAccount(request, response);
		}
		if(uri.contains("/account/new")) {
			newAccount(request, response);
		}
		if(uri.contains("/product/add")) {
			addProduct(request, response);
		}
		if(uri.contains("/product/update")) {
			updateProduct(request, response);
		}
		if(uri.contains("/product/remove")) {
			removeProduct(request, response);
		}
		if(uri.contains("/product/new")) {
			newProduct(request, response);
		}
		request.getRequestDispatcher("/views/admin/admin.jsp").forward(request, response);
	}
	
	public void getProductIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		List<Video> li = new ArrayList<>();
		if(list.size()>5) {
			for(int i=0;i<5;i++) {
				li.add(list.get(i));
			}
			request.setAttribute("products", li);
		}
		else 
			request.setAttribute("products", list);
	}
	
	public void getAccountIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		List<Users> li = new ArrayList<>();
		if(accList.size()>5) {
			for(int i=0;i<5;i++) {
				li.add(accList.get(i));
			}
			request.setAttribute("accounts", li);
		}
		else 
			request.setAttribute("accounts", accList);
	}
	
	public void getInteractIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			if(request.getParameter("date") != null) {
				date = format.parse(request.getParameter("date"));
				request.setAttribute("date", request.getParameter("date"));
			}
			likes = faDao.findByDate(date).size();
			cmts = cmtDao.findByDate(date).size();
			shares = shareDao.findByDate(date).size();
			List<Long> li = faDao.findHighestByDate(date);
			if(li.size()!=0)
				highestLike = li.get(0);
			else 
				highestLike = 0;
			List<Long> cm = cmtDao.findHighestByDate(date);
			if(cm.size()!=0)
				highestCmt = cm.get(0);
			else 
				highestCmt = 0;
			List<Long> sha = shareDao.findHighestByDate(date);
			if(sha.size()!=0)
				highestShare = sha.get(0);
			else 
				highestShare = 0;
			request.setAttribute("likes", likes);
			request.setAttribute("highestLike", highestLike);
			request.setAttribute("cmts", cmts);
			request.setAttribute("highestCmt", highestCmt);
			request.setAttribute("shares", shares);
			request.setAttribute("highestShare", highestShare);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void getProductAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		list = vdDao.findAllAdmin();
		request.setAttribute("products", list);
		request.setAttribute("showAllProduct", true);
	}
	
	public void getAccountAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		accList = usDao.findAll();
		request.setAttribute("accounts", accList);
		request.setAttribute("showAllAccount", true);
	}
	
	public void addAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Users user = new Users();
		try {
			DateTimeConverter dtc = new DateConverter(new Date());
			dtc.setPattern("dd/MM/yyyy");
			ConvertUtils.register(dtc, Date.class);
			BeanUtils.populate(user, request.getParameterMap());
			if(user.getId().equals("") || user.getPassword().equals("") || user.getEmail().equals("") || user.getFullname().equals(""))
				request.setAttribute("error_account", "Data empty!");
			else if(!user.getEmail().matches("\\w+@\\w+(\\.\\w+){1,2}"))
				request.setAttribute("error_account", "Email invalid!");
			else if(usDao.findById(user.getId()) != null)
				request.setAttribute("error_account", "Username exists!");
			else {
				usDao.create(user);
				request.setAttribute("success_account", "Create success!");
			}
			request.setAttribute("us", user);
			accList = usDao.findAll();
			getAccountIndex(request, response);
			request.setAttribute("showAccount", true);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			request.setAttribute("us", user);
			accList = usDao.findAll();
			getAccountIndex(request, response);
			request.setAttribute("showAccount", true);
			request.setAttribute("error_account", "Create fail!");
		}
	}
	
	public void updateAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Users user = new Users();
		try {
			DateTimeConverter dtc = new DateConverter(new Date());
			dtc.setPattern("dd/MM/yyyy");
			ConvertUtils.register(dtc, Date.class);
			BeanUtils.populate(user, request.getParameterMap());
			if(user.getId().equals("") || user.getPassword().equals("") || user.getEmail().equals("") || user.getFullname().equals(""))
				request.setAttribute("error_account", "Data empty!");
			else if(!user.getEmail().matches("\\w+@\\w+(\\.\\w+){1,2}"))
				request.setAttribute("error_account", "Email invalid!");
			else if(usDao.findById(user.getId()) == null)
				request.setAttribute("error_account", "Username not exists!");
			else {
				usDao.update(user);
				request.setAttribute("success_account", "Update success!");
			}
			request.setAttribute("us", user);
			accList = usDao.findAll();
			getAccountIndex(request, response);
			request.setAttribute("showAccount", true);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			request.setAttribute("error_account", "Update fail!");
			request.setAttribute("us", user);
			accList = usDao.findAll();
			getAccountIndex(request, response);
			request.setAttribute("showAccount", true);
		}
	}
	
	public void removeAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Users user = new Users();
		try {
			if(request.getParameter("id") != null)
				user.setId(request.getParameter("id").toString());
			else 
				user.setId("");
			if(user.getId().equals(""))
				request.setAttribute("error_account", "Id empty!");
			else if(usDao.findById(user.getId()) == null)
				request.setAttribute("error_account", "Username not exists!");
			else {
				usDao.remove(user.getId());
				request.setAttribute("success_account", "Remove success!");
			}
			request.setAttribute("us", user);
			accList = usDao.findAll();
			getAccountIndex(request, response);
			request.setAttribute("showAccount", true);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			request.setAttribute("error_account", "Remove fail!");
			request.setAttribute("us", user);
			accList = usDao.findAll();
			getAccountIndex(request, response);
			request.setAttribute("showAccount", true);
		}
	}
	
	public void newAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		accList = usDao.findAll();
		getAccountIndex(request, response);
		request.setAttribute("showAccount", true);
	}
	
	public void addProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Video video = new Video();
		try {
			DateTimeConverter dtc = new DateConverter(new Date());
			dtc.setPattern("dd/MM/yyyy");
			ConvertUtils.register(dtc, Date.class);
			BeanUtils.populate(video, request.getParameterMap());
			if(video.getId().equals("") || video.getPoster().equals("") || video.getDescription().equals("") || video.getTitle().equals("") || video.getCategory().equals(""))
				request.setAttribute("error_product", "Data empty!");
			else if(video.getViews() < 0)
				request.setAttribute("error_product", "Views invalid!");
			else if(vdDao.findById(video.getId()) != null)
				request.setAttribute("error_product", "Video ID exists!");
			else {
				vdDao.create(video);
				request.setAttribute("success_product", "Create success!");
			}
			request.setAttribute("vd", video);
			list = vdDao.findAllAdmin();
			getProductIndex(request, response);
			request.setAttribute("showProduct", true);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			request.setAttribute("vd", video);
			list = vdDao.findAllAdmin();
			getProductIndex(request, response);
			request.setAttribute("showProduct", true);
			request.setAttribute("error_product", "Create fail!");
		}
	}
	
	public void updateProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Video video = new Video();
		try {
			DateTimeConverter dtc = new DateConverter(new Date());
			dtc.setPattern("dd/MM/yyyy");
			ConvertUtils.register(dtc, Date.class);
			BeanUtils.populate(video, request.getParameterMap());
			if(video.getId().equals("") || video.getPoster().equals("") || video.getDescription().equals("") || video.getTitle().equals("") || video.getCategory().equals(""))
				request.setAttribute("error_product", "Data empty!");
			else if(video.getViews() < 0)
				request.setAttribute("error_product", "Views invalid!");
			else if(vdDao.findById(video.getId()) == null)
				request.setAttribute("error_product", "Video not exists!");
			else {
				vdDao.update(video);
				request.setAttribute("success_product", "Update success!");
			}
			request.setAttribute("vd", video);
			list = vdDao.findAllAdmin();
			getProductIndex(request, response);
			request.setAttribute("showProduct", true);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			request.setAttribute("vd", video);
			list = vdDao.findAllAdmin();
			getProductIndex(request, response);
			request.setAttribute("showProduct", true);
			request.setAttribute("error_product", "Update fail!");
		}
	}
	
	public void removeProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Video video = new Video();
		try {
			if(request.getParameter("id") != null)
				video.setId(request.getParameter("id"));
			else 
				video.setId("");
			if(video.getId().equals(""))
				request.setAttribute("error_product", "Video ID empty!");
			else if(vdDao.findById(video.getId()) == null)
				request.setAttribute("error_product", "Video not exists!");
			else {
				vdDao.remove(video.getId());
				request.setAttribute("success_product", "Remove success!");
			}
			request.setAttribute("vd", video);
			list = vdDao.findAllAdmin();
			getProductIndex(request, response);
			request.setAttribute("showProduct", true);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			request.setAttribute("vd", video);
			list = vdDao.findAllAdmin();
			getProductIndex(request, response);
			request.setAttribute("showProduct", true);
			request.setAttribute("error_product", "Remove fail!");
		}
	}
	
	public void newProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		list = vdDao.findAllAdmin();
		System.out.print(list.size());
		getProductIndex(request, response);
		request.setAttribute("showProduct", true);
	}
}
