package com.mistus.Question.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.mistus.Question.model.QuestionServices;
import com.mistus.Question.model.QuestionVO;



@WebServlet("/Hangman/QusetionServlet.do")
public class QuestionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public QuestionServlet() {
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		
		//QuestionCheck , 取出尚未驗證完成Question List後抽選一組回傳 
		if(action.equals("QuestionCheck")){
			
			QuestionServices QS = new QuestionServices();	
			response.setContentType("text/html; charset=UTF-8"); 
			PrintWriter out = response.getWriter();
			response.setCharacterEncoding("UTF-8");
			out.print(RandomQuestion(QS.getAnalyticsQuestion()));
			out.close();
		}	
		
		if(action.equals("getCorrectQuestion")){
			QuestionServices QS = new QuestionServices();	
			response.setContentType("text/html; charset=UTF-8"); 
			PrintWriter out = response.getWriter();
			response.setCharacterEncoding("UTF-8");
			out.print(RandomQuestion(QS.getCorrectQuestion()));
			out.close();
		}
		
	}
	
	//依SQL收到的 List 中隨機挑出一組問題
	public List<JSONObject> RandomQuestion(List<QuestionVO> QuestionList){
		
		List<JSONObject> RandomList = new LinkedList<JSONObject>();
		List<JSONObject> ReturnJsonList = new LinkedList<JSONObject>();
		int count = 0;
		
		for(QuestionVO QuestionVO:QuestionList){
			
			//由1開始
			count++;
			JSONObject JsonObject = new JSONObject();	
			JsonObject.put("RandomNumber", count);
			JsonObject.put("QuestionNumber", QuestionVO.getQuestionNumber()); //PK
			JsonObject.put("Question", QuestionVO.getQuestion());
			JsonObject.put("Answer", QuestionVO.getAnswer());
			RandomList.add(JsonObject);
			
			
		}
		
//		int Dick = (int)((Math.random()*6)+1);  //隨機產生1-6
		int Dick = (int)((Math.random() * count)+1);  //隨機產生1-6
		
		for(JSONObject Random : RandomList){
			
			if((int)Random.get("RandomNumber") == Dick){
				JSONObject JsonObject = new JSONObject();
				JsonObject.put("QuestionNumber", ((Integer)Random.get("QuestionNumber")));
				JsonObject.put("Question", ((String)Random.get("Question")));
				JsonObject.put("Answer", ((String)Random.get("Answer")));
				ReturnJsonList.add(JsonObject);
			}
			
		}
		
//		System.out.println(ReturnJsonList);
		return ReturnJsonList;
	}

}
