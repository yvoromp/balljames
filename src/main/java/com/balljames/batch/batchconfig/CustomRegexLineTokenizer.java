package com.balljames.batch.batchconfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.batch.item.file.transform.RegexLineTokenizer;
import org.springframework.util.Assert;

public class CustomRegexLineTokenizer extends RegexLineTokenizer{
	
	private Pattern pattern;
	
	@Override
	protected List<String> doTokenize(String line) {
		Matcher matcher = pattern.matcher(line);
		boolean matchFound = matcher.find();

		if (matchFound) {
			List<String> tokens = new ArrayList<>(matcher.groupCount());
			for (int i = 1; i <= matcher.groupCount(); i++) {
				if(matcher.group(i).equals("")) {
					tokens.add("0.000");
					continue;
				}
				tokens.add(matcher.group(i));
			}
			return tokens;
		}
		return Collections.emptyList();
	}
  
  	@Override
	public void setRegex(String regex) {
		Assert.hasText(regex, "a valid regex is required");
		this.pattern = Pattern.compile(regex);
	}
}
