<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="database_connection.*,quiz_model.*,quiz_web.*,java.sql.*,java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	Quiz quiz = (Quiz) request.getSession().getAttribute("Quiz");
	List<Question> questions = quiz.getQuestions();
	String questionNumStr = request.getParameter("question");
	int questionNum;
	if (questionNumStr == null) questionNum = 0;
	else questionNum = Integer.parseInt(questionNumStr);
	Question q = questions.get(questionNum);
	int answerNum = q.getAnswerNum();
	ArrayList<String> ansList = new ArrayList<String> ();
	if (answerNum == 1) {
		String ans = request.getParameter("answer" + Integer.toString(questionNum));
		request.getSession().setAttribute("answer"+Integer.toString(questionNum), ans);
		ansList.add(ans);
	} else {
		for (int i = 0; i < answerNum; i++) {
			String ans = request.getParameter("answer"+Integer.toString(questionNum)+"_"+Integer.toString(i));
			request.getSession().setAttribute("answer"+Integer.toString(questionNum)+"_"+Integer.toString(i), ans);
	        ansList.add(ans);
		}
	}	
	int curScore = q.getScore(ansList);
	
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Quiz - <%=quiz.getName()%></title>
</head>
<body>
<%
	String des;
	String button;
	if(questionNum == questions.size() - 1) {
		des = "QuizResultPage.jsp";
		button = "See result";
	}
	else {
		des = "QuizMultiplePageWithCorrection.jsp";
		button = "Next";
	}
	out.println("<form action=\"" + des + "\" method = \"POST\">");
	String input = "<input type=\"hidden\" name=\"question\" value=\""+ Integer.toString(questionNum)+"\">";
	out.print(input);
	//System.out.println(input);
	out.print(q.getHTMLwithQuestionResult(questionNum, ansList, curScore));
%>
<input type="submit" value="Submit" disabled>
<input type="submit" value="<%=button%>">
</form>
</body>
</html>