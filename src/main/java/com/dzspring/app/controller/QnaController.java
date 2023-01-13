package com.dzspring.app.controller;

import static com.dzspring.app.controller.ResponseMessage.getJSONHeader;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dzspring.app.entity.Member;
import com.dzspring.app.entity.Qna;
import com.dzspring.app.service.impl.QnaService;

@RestController
@RequestMapping("/qna")
public class QnaController {

	private final QnaService qnaService;
	
	@Autowired
	public QnaController(QnaService qnaService) {
		this.qnaService = qnaService;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<ResponseMessage> get(@RequestParam HashMap<String, Object> map) {
		ResponseMessage message = new ResponseMessage();
		Map<String, Object> data = new HashMap<>();
		data.put("list", qnaService.list(map));
		data.put("total", qnaService.getCount(map));
		message.setData(data);
		return new ResponseEntity<>(message, getJSONHeader(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/view/{id}", method=RequestMethod.GET)
	public ResponseEntity<ResponseMessage> view(@PathVariable int id) {
		ResponseMessage message = new ResponseMessage();
		Map<String, Object> data = new HashMap<>();
		data.put("view", qnaService.view(id).get());
		data.put("answers", qnaService.answerlist(id));
		message.setData(data);
		return new ResponseEntity<>(message, getJSONHeader(), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<ResponseMessage> post(@RequestBody Qna qna, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Member member = (Member) session.getAttribute("member");
		ResponseMessage message = new ResponseMessage();
		if (member == null) {
			message.setData(false);
		} else {
			qna.setMemberId(member.getId());
			message.setData(qnaService.insert(qna));
		}
		return new ResponseEntity<>(message, getJSONHeader(), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<ResponseMessage> put(@RequestBody Qna qna, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Member member = (Member) session.getAttribute("member");
		ResponseMessage message = new ResponseMessage();
		message.setData(qnaService.update(member, qna));
		return new ResponseEntity<>(message, getJSONHeader(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<ResponseMessage> delete(@PathVariable int id, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Member member = (Member) session.getAttribute("member");
		ResponseMessage message = new ResponseMessage();
		if (member == null) {
			message.setData(false);
		} else {
			message.setData(qnaService.delete(member, id));
		}
		return new ResponseEntity<>(message, getJSONHeader(), HttpStatus.OK);
	}
}
