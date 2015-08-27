package com.appmanager;

import com.common.HardCodedProperties;
import com.job.core.MantraJobRegistry;
import com.job.core.base.MantraJobParam;
import com.job.html.HtmlProperties;
import org.apache.log4j.Logger;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.util.*;

public class JobProperties {

	private static Logger log = Logger.getLogger(JobProperties.class);
	public List<String> jobTypes = new ArrayList<String>();
	public Map<String, String> jobTypesToClass = new HashMap<String, String>();

	public Map<String, Map<String, HtmlProperties>> jobParams = new LinkedHashMap<String, Map<String, HtmlProperties>>();

	public JobProperties() {
		Reflections reflections = new Reflections(HardCodedProperties._BASE_JOB_FOLDER);
		Set<Class<?>> annotated = reflections
				.getTypesAnnotatedWith(MantraJobRegistry.class);

		for (Class<?> controller : annotated) {
			MantraJobRegistry request = controller
					.getAnnotation(MantraJobRegistry.class);
			jobTypes.add(request.jobType());
			Object objectForDefaulValue = null;
			try {
				objectForDefaulValue = controller.newInstance();
			} catch (Exception e) {
				// Ignore
				log.equals("cannot create object for default value " + e);
			}

			Map<String, HtmlProperties> temp = new LinkedHashMap<String, HtmlProperties>();
			for (Field f : getAllField(controller)) {
				f.setAccessible(true);
				MantraJobParam jobParam = f.getAnnotation(MantraJobParam.class);
				if (jobParam != null) {
					HtmlProperties jf = new HtmlProperties();
					jf.setId(f.getName());
					jf.setDisplayName(jobParam.displayName());
					jf.setRequired(jobParam.isRequired());
					jf.setHtmlWidth(jobParam.htmlFieldWidth());
					jf.setReadOnly(jobParam.readOnlyField());
					jf.setFieldType(jobParam.fieldType().name().toUpperCase());
					try {
						if (objectForDefaulValue != null){
							Object value = f.get(objectForDefaulValue);
							if(value != null)
								jf.setDefaultValue(value+ "");
						}
					} catch (Exception e) {
						// Ignore
						log.equals("cannot get default value " + e);
					}
					temp.put(f.getName(), jf);
				}
			}

			jobTypesToClass.put(request.jobType(), controller.getName());
			jobParams.put(request.jobType(), temp);
		}

		Collections.unmodifiableMap(jobParams);
		Collections.unmodifiableList(jobTypes);
	}

	public static List<Field> getAllField(Class clazz) {
		Field[] superClassVariable = clazz.getSuperclass().getDeclaredFields();
		Field[] currentVariable = clazz.getDeclaredFields();

		List<Field> allFileds = new ArrayList<Field>();
		allFileds.addAll(Arrays.asList(superClassVariable));
		allFileds.addAll(Arrays.asList(currentVariable));

		return allFileds;
	}

}
