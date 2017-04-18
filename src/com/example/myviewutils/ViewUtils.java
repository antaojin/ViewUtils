package com.example.myviewutils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

public class ViewUtils {

	public static void inject(Activity activity){
		try {
			bindView(activity);
			bindClick(activity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 给Activity中所有添加了@ViewInject注解的成员变量绑定控件
	 * 
	 * @param activity
	 * 
	 * 步骤：
	 * 1. 获取MainActivity的字节码
	 * 2. 获取MainActivity中的属性[]
	 * 3. 遍历属性 Filed 
	 * 4. 获取Filed上的特定的注解（ViewInject）
	 * 5. 如果有ViewInject注解
	 * 6. 获取到ViewInject上的resId
	 * 7. View view = MainActivity.findViewById(resId);
	 * 8. 将view控件设置给Filed
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	private static void bindView(Activity activity) throws IllegalAccessException, IllegalArgumentException {
		Class<? extends Activity> clazz = activity.getClass();
		//获取字节码中所有声明的属性
		Field[] fields = clazz.getDeclaredFields();
		for(Field field : fields){
			//判断这个field是否是我们需要的
			ViewInject viewInject = field.getAnnotation(ViewInject.class);
			if (viewInject!=null) {
				int resId = viewInject.value();
				View view = activity.findViewById(resId);
				//将view设置给Field
				field.setAccessible(true);
				field.set(activity, view);
			}
		}
	}
	/**
	 * @param activity
	 * 步骤：
	 * 1. 获取activity的字节码
	 * 2. 遍历字节码中所有声明了的，Method
	 * 3. 获取Method上的自定义注解（OnClick）
	 * 4. 如果获取到了OnClick注解对象
	 * 5. 获取OnClick的ids
	 * 6. 遍历ids ，resID
	 * 7. View view = activity.findViewById(resId);
	 * 8. 给view绑定点击事件 view.setOnClickListener(){public void onClick(View view){  //TODO    }};
	 * 9. 在onClick（View view）方法中，反射调用method
	 */
	private static void bindClick(final Activity activity) {
		Class<? extends Activity> clazz = activity.getClass();
		//获取clazz中所有的方法
		Method[] methods = clazz.getDeclaredMethods();
		for(final Method method : methods){
			//判断这个method是否是我们需要的
			//获取method上的特定的注解
			OnClick onClick = method.getAnnotation(OnClick.class);
			if (onClick!=null) {
				//获取OnClick注解对象上的ids
				int[] resIds = onClick.value();
				//遍历resIds
				for(int resId : resIds){
					final View view = activity.findViewById(resId);
					//给view绑定点击事件
					view.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
//							Log.d("tag", v.toString()+"/被点了");
							//反射调用method
							method.setAccessible(true);
							//参数1：目标对象
							//参数2：传入的对象，其实就是被点击的对象
							try {
								method.invoke(activity, view);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				}
			}
		}
	}
}













