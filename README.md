#Android基础day14-Fragment&动画
***
##1. Fragment简介（**）
1. 简介

		A Fragment represents a behavior or a portion of user interface in an Activity. You can combine multiple fragments in a single activity to build a multi-pane UI and reuse a fragment in multiple activities. You can think of a fragment as a modular section of an activity, which has its own lifecycle, receives its own input events, and which you can add or remove while the activity is running (sort of like a "sub activity" that you can reuse in different activities).


##2. Fragment的使用（*****）
###2.1 自定义MyFragment extendsFragment（android.support.v4.app.Fragment）并且覆写其生命周期方法

###2.2 为MyFragment编写UI（编写布局文件）

###2.3 将MyFragment添加到Activity中
1. Declare the fragment inside the activity's layout file.

2. Or, programmatically add the fragment to an existing ViewGroup.


###2.4 使用Fragment时的bug
1. 在Activity的布局文件中使用fragment标签时，必须添加一个id属性或者tag属性

			04-13 01:33:05.408: E/AndroidRuntime(1662): Caused by: java.lang.IllegalArgumentException: Binary XML file line #12: Must specify unique android:id, android:tag, or have a parent with an id for com.example.fragment.FristFragment
2. 如果使用的是v4包下的Fragment，那么Activity就必须使用FragmentActivity

		
			04-13 01:37:36.593: E/AndroidRuntime(1730): Caused by: java.lang.ClassCastException: com.example.fragment.FristFragment cannot be cast to android.app.Fragment
			04-13 01:37:36.593: E/AndroidRuntime(1730): 	at android.app.Fragment.instantiate(Fragment.java:577)
			04-13 01:37:36.593: E/AndroidRuntime(1730): 	at android.app.Fragment.instantiate(Fragment.java:552)
			04-13 01:37:36.593: E/AndroidRuntime(1730): 	at android.app.Activity.onCreateView(Activity.java:4656)
			04-13 01:37:36.593: E/AndroidRuntime(1730): 	at android.view.LayoutInflater.createViewFromTag(LayoutInflater.java:680)
			04-13 01:37:36.593: E/AndroidRuntime(1730): 	... 21 more
3. 同一份fragment对象往Activity添加多次异常

			04-13 01:52:32.696: E/AndroidRuntime(1829): java.lang.IllegalStateException: Fragment already added: SecondFragment{b656d8c0 #1 id=0x7f080001}
			04-13 01:52:32.696: E/AndroidRuntime(1829): 	at android.support.v4.app.FragmentManagerImpl.addFragment(FragmentManager.java:1192)




##3. Fragment的生命周期（***）

##4. Fragment的切换（*****）
###4.1 通过replace
	缺点：每次切换时都会老的Fragment给杀死，当重新切换回来的时候又重新创建，因此效率可能有影响。
###4.2 通过add然后hide方式
	
			getSupportFragmentManager().beginTransaction()
		.add(R.id.fl_content, conversationFragment)
		.add(R.id.fl_content, contactFragment)
		.add(R.id.fl_content, dongtaiFragment)
		.hide(contactFragment)
		.hide(dongtaiFragment)
		.commit();
	优点：只在第一次添加的时候走一次生命周期方法，之后的hide和show对其生命周期没有任何影响。

##5. Fragment之间的通信（****）

				//获取到RightFragment
				//然后调用RightFragment的setTitleAndImage
				//在Fragment中获取Fragment管理器
				FragmentManager fragmentManager = getFragmentManager();
				RightFragment rightFragment = (RightFragment) fragmentManager.findFragmentByTag("right");
				rightFragment.setTitleAndImage(titles[position], resIDs[position]);

##6. Android中的动画（**）

###1. （逐）帧动画（*）
1. 在res目录下创建一个帧动画的资源文件 res->drawable->animation-list
2. 给ImageView设置帧动画资源
	1. iv.setImageResource(R.drawable.toukan_anim);
	2.   android:src="@drawable/toukan_anim"       这两种方式等价的

3. 通过iv获取AnimationDrawable 

4. 执行动画

		
		iv.setImageResource(R.drawable.wifi_anim);
		
		AnimationDrawable animationDrawable = (AnimationDrawable) iv.getDrawable();
		if (animationDrawable.isRunning()) {
			animationDrawable.stop();
		}
		animationDrawable.start();


###2.  补间（Tween Animation）动画（***）
核心类：Animation

1. 透明
	1. 通过纯代码的方式

				
			AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
			//设置时长
			alphaAnimation.setDuration(100);
			//执行多次
			alphaAnimation.setRepeatCount(Animation.INFINITE);//无限循环
			//设置重复的模式
			alphaAnimation.setRepeatMode(Animation.REVERSE);
			//让IamgeView执行动画
			iv.startAnimation(alphaAnimation);				

	2. 通过xml文件的方式
		1. 编写tween animation 动画资源

				<?xml version="1.0" encoding="utf-8"?>
				<alpha xmlns:android="http://schemas.android.com/apk/res/android"
  	 			 android:fromAlpha="0"
  	 			 android:toAlpha="1"
   				 android:duration="2000"
   	 				android:repeatCount="1"
   				 android:fillAfter="true"
  	 			 android:repeatMode="reverse"
  	 			 >
				</alpha>
		2. 通过AnimationUtils，加载动画资源，然后让ImageView执行

				Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha_anima);
				iv.startAnimation(animation);

2. 平移

3. 缩放

4. 旋转

5. 动画集合

	
			AnimationSet animationSet = new AnimationSet(true);
		
		ScaleAnimation scaleAnimation = new ScaleAnimation(1, 10, 1, 10, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		scaleAnimation.setDuration(1000);
		scaleAnimation.setRepeatCount(Animation.INFINITE);
		scaleAnimation.setRepeatMode(Animation.REVERSE);
		
		
		RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		rotateAnimation.setDuration(1000);
		rotateAnimation.setRepeatCount(Animation.INFINITE);
		rotateAnimation.setRepeatMode(Animation.REVERSE);
		
		//将 两个动画添加到animationSet
		animationSet.addAnimation(scaleAnimation);
		animationSet.addAnimation(rotateAnimation);
		
		iv.startAnimation(animationSet);


###3.属性（值）动画（***）
核心类：ObjectAnimator

1. 透明
	1. 纯代码
		
			ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(iv, "alpha", 0,1,0.5f,1,0,0.5f);
			objectAnimator.setDuration(3000);
			objectAnimator.start();


	2. 使用xml
		1. 在res/animator/目录下创建ObjectAnimator动画资源文件
		2. 使用AnimatorInflater将属性动画资源转换为对象
		3. 执行

					//将alpha_anima.xml转换为ObjectAnimator对象
					ObjectAnimator animator = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.alpha_anim);
					
					//设置目标控件
					animator.setTarget(iv);
					
					animator.start();

2. 平移


3. 缩放


4. 旋转


5. 动画集合


6. 监听动画的开始和结束

			objectAnimator.addListener(new AnimatorListener() {
			
			@Override
			public void onAnimationStart(Animator animation) {
				Toast.makeText(MainActivity.this, "动画开始执行了", Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onAnimationRepeat(Animator animation) {
				
			}
			
			@Override
			public void onAnimationEnd(Animator animation) {
				Toast.makeText(MainActivity.this, "动画执行完了", Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onAnimationCancel(Animator animation) {
				
			}
		});


7. 监听动画执行的百分比

	objectAnimator.addUpdateListener(new AnimatorUpdateListener() {
			
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				float animatedFraction = animation.getAnimatedFraction();
				Log.d("tag", "animatedFraction="+animatedFraction);
			}
		});


	


##7. 新API
1. 判断Fragment是否已经被添加到Activity中了
	
			if (secondFragment.isAdded()) {
			Toast.makeText(this, "已经添加过了", Toast.LENGTH_SHORT).show();
			return;
		}
2. 如何在Fragment中获取Context对象

	FragmentActivity activity = getActivity();
