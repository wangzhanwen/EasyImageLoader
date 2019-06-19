# EasyImageLoader
图片加载框架


        // 配置
        ImageLoaderConfig.Builder builder = new ImageLoaderConfig.Builder();
        builder.setTheadCount(3) //线程数量
                .setLoadPolicy(new ReversePolicy()) //加载策略
                .setCachePolicy(new DoubleCache(this)) //缓存策略
                .setLoadingImage(R.drawable.loading)
                .setLoadFailImage(R.drawable.not_found);

        ImageLoaderConfig config = builder.build();
        // 初始化
        SimpleImageLoader.getInstance(config)
               .displayImage(imageView, imgUrl);



Step 1. Add the JitPack repository to your build file

  Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.wangzhanwen:EasyImageLoader:1.0.0'
	}


