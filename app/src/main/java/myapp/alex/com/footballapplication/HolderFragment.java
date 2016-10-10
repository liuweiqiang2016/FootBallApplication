package myapp.alex.com.footballapplication;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class HolderFragment extends Fragment{
	
//	private String url;

	WebView mWebView;

	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
//	private static final String ARG_PARAM2 = "param2";

	// TODO: Rename and change types of parameters
	private String url;
	private String mParam2;

//	public HolderFragment(String url) {
//		this.url = url;
//	}
	public HolderFragment() {
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param param1 Parameter 1.
	 * @return A new instance of fragment AboutFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static HolderFragment newInstance(String param1) {
		HolderFragment fragment = new HolderFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
//		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}


	private Handler mHandler=new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what==1){
				//加载新闻
				mWebView.loadUrl(url);

			}
			if (msg.what==2){
//				网络请求失败
				try {
					final RelativeLayout webParentView= (RelativeLayout) mWebView.getParent();
					View view=View.inflate(getActivity(),R.layout.error_view,null);
					Button btn= (Button) view.findViewById(R.id.btn_retry);
					btn.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							if(webParentView!=null){
								webParentView.removeAllViews();
							}
							if (mWebView!=null){
								mWebView.reload();
							}
							if (webParentView!=null){
								webParentView.addView(mWebView);
							}
						}
					});
					LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.MATCH_PARENT);
					if (webParentView!=null){
						webParentView.removeAllViews();
						webParentView.addView(view, 0, lp);
					}
				}catch (Exception e){

				};

			}
		}
	};
	
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			url = getArguments().getString(ARG_PARAM1);
//			mParam2 = getArguments().getString(ARG_PARAM2);
		}
	}

	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_holder, container, false);
		 mWebView
				= (WebView) v.findViewById(R.id.mWebView);
		mWebView.getSettings().setJavaScriptEnabled(true);
		WebSettings wv = mWebView.getSettings();
		wv.setJavaScriptEnabled(true);
		wv.setJavaScriptCanOpenWindowsAutomatically(true);
		wv.setAppCacheEnabled(true);
		mWebView.setVerticalScrollBarEnabled(false);
		mWebView.setHorizontalScrollBarEnabled(false);

		mWebView.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				return true;
			}
		});

		mWebView.setWebViewClient(new WebViewClient() {

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {

				view.loadUrl(url);
//				mHandler.sendEmptyMessage(1);
				return true;
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);

				Log.e("onPageStarted: ","开始");
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);

				Log.e("onPageFinished: ","结束");
			}

			@Override
			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
				super.onReceivedError(view, errorCode, description, failingUrl);
//				Log.e( "onReceivedError: ", description);
				mHandler.sendEmptyMessage(2);
			}
		});

		mHandler.sendEmptyMessage(1);
		return v;
	}

}
