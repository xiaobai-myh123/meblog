package com.myh.api.pojo;

import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
**2020年9月14日--下午8:44:18
**@version:
**莫耀华:
**@Description:  api
*/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result extends HashMap<String, Object> {

	private int code; // 200是正常，非200表示异常
	private String msg;
	private Object data;

	public static Result succ(Object data) {
		return succ(200, "请求成功", data);
	}

	public static Result succ(int code, String msg, Object data) {
		Result r = new Result();
		r.setCode(code);
		r.setMsg(msg);
		r.setData(data);
		return r;
	}

	public static Result fail(String msg) {
		return fail(400, msg, null);
	}

	public static Result fail(String msg, Object data) {
		return fail(400, msg, data);
	}

	public static Result ok() {
		return new Result();
	}
	public static Result ok(int code,String msg) {
		Result r = new Result();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}
	public static Result error(int code, String msg) {
		Result r = new Result();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	public static Result fail(int code, String msg, Object data) {
		Result r = new Result();
		r.setCode(code);
		r.setMsg(msg);
		r.setData(data);
		return r;
	}

	@Override
	public Result put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}
