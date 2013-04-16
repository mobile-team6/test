package com.example.ex;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

public class Main_Activity extends Activity{
	/** Called when the activity is first created. */
	
	private int flag=0;
	private int sYear;
	private int eYear;
	private int tYear;
	private int sMonth;
	private int eMonth;
	private int tMonth;
	private int sDay;
	private int eDay;
	private int tDay;
	private Button btn_start_date;
	private Button btn_end_date;

	private static final int LAUNCHED_ACTIVITY = 0;
	private static final int DATE_DIALOG_ID1 = 1;
	private static final int DATE_DIALOG_ID2 = 2;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//* Tab *//
		TabHost tabHost = (TabHost)findViewById(R.id.tab_host);
		tabHost.setup();

		// Tab1 Setting
		TabSpec tabSpec1 = tabHost.newTabSpec("Tab1");
		tabSpec1.setIndicator("홈"); // Tab Subject
		tabSpec1.setContent(R.id.tab_view1); // Tab Content
		tabHost.addTab(tabSpec1);

		// Tab2 Setting
		TabSpec tabSpec2 = tabHost.newTabSpec("Tab2");
		tabSpec2.setIndicator("발전\n정보"); // Tab Subject
		tabSpec2.setContent(R.id.tab_view2); // Tab Content
		tabHost.addTab(tabSpec2);

		// Tab3 Setting
		TabSpec tabSpec3 = tabHost.newTabSpec("Tab3");
		tabSpec3.setIndicator("효율\n정보"); // Tab Subject
		tabSpec3.setContent(R.id.tab_view3); // Tab Content
		tabHost.addTab(tabSpec3);

		// Tab4 Setting
		TabSpec tabSpec4 = tabHost.newTabSpec("Tab4");
		tabSpec4.setIndicator("정보\n분석"); // Tab Subject
		tabSpec4.setContent(R.id.tab_view4); // Tab Content
		tabHost.addTab(tabSpec4);

		// Tab5 Setting
		TabSpec tabSpec5 = tabHost.newTabSpec("Tab5");
		tabSpec5.setIndicator("경보\n이력"); // Tab Subject
		tabSpec5.setContent(R.id.tab_view5); // Tab Content
		tabHost.addTab(tabSpec5);

		// show First Tab Content
		tabHost.setCurrentTab(0);


		//* Graph *//

		// 표시할 수치값  
		List<double[]> values = new ArrayList<double[]>(); 
		values.add(new double[] { 14230, 12300, 14240, 15244, 15900, 19200,
				22030, 21200, 19500, 15500, 12600, 14000 });  

		// 그래프 출력을 위한 그래픽 속성 지정객체
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();

		// 상단 표시 제목과 글자 크기  
		renderer.setChartTitle("발전 정보");  
		renderer.setChartTitleTextSize(20);  

		// 분류에 대한 이름  
		String[] titles = new String[] { "발전량" };  

		// 항목을 표시하는데 사용될 색상값  
		int[] colors = new int[] { Color.YELLOW };  

		// 분류명 글자 크기 및 각 색상 지정  
		renderer.setLegendTextSize(15);  
		int length = colors.length;  
		for (int i = 0; i < length; i++) {  
			SimpleSeriesRenderer r = new SimpleSeriesRenderer();  
			r.setColor(colors[i]);  
			renderer.addSeriesRenderer(r);  
		}  

		// X,Y축 항목이름과 글자 크기  
		renderer.setXTitle("월");  
		renderer.setYTitle("kwh");  
		renderer.setAxisTitleTextSize(12);

		// 수치값 글자 크기 / X축 최소,최대값 / Y축 최소,최대값  
		renderer.setLabelsTextSize(10);  
		renderer.setXAxisMin(0.5);  
		renderer.setXAxisMax(12.5);  
		renderer.setYAxisMin(0);  
		renderer.setYAxisMax(24000);  

		// X,Y축 라인 색상  
		renderer.setAxesColor(Color.WHITE);  
		// 상단제목, X,Y축 제목, 수치값의 글자 색상  
		renderer.setLabelsColor(Color.CYAN);  

		// X축의 표시 간격  
		renderer.setXLabels(12);  
		// Y축의 표시 간격  
		renderer.setYLabels(5);  

		// X,Y축 정렬방향  
		renderer.setXLabelsAlign(Align.LEFT);  
		renderer.setYLabelsAlign(Align.LEFT);  
		// X,Y축 스크롤 여부 ON/OFF  
		renderer.setPanEnabled(false, false);  
		// ZOOM기능 ON/OFF  
		renderer.setZoomEnabled(false, false);  
		// ZOOM 비율  
		renderer.setZoomRate(1.0f);  
		// 막대간 간격  
		renderer.setBarSpacing(0.5f);  

		// 설정 정보 설정  
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();  
		for (int i = 0; i < titles.length; i++) {  
			CategorySeries series = new CategorySeries(titles[i]);  
			double[] v = values.get(i);  
			int seriesLength = v.length;  
			for (int k = 0; k < seriesLength; k++) {  
				series.add(v[k]);  
			}  
			dataset.addSeries(series.toXYSeries());  
		}  

		// 그래프 객체 생성  
		GraphicalView gv = ChartFactory.getBarChartView(this, dataset, renderer, Type.STACKED);  

		// 그래프를 LinearLayout에 추가  
		LinearLayout tab_view2 = (LinearLayout)findViewById(R.id.tab_view2);  
		tab_view2.addView(gv);

		// 경보이력
		btn_start_date = (Button)findViewById(R.id.btn_start_date);
		btn_start_date.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog(DATE_DIALOG_ID1);
			}
		});
		
		btn_end_date = (Button)findViewById(R.id.btn_end_date);
		btn_end_date.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog(DATE_DIALOG_ID2);
			}
		});
		
		final Calendar c = Calendar.getInstance();
        
        tYear = c.get(Calendar.YEAR);
        tMonth = c.get(Calendar.MONTH);
        tDay = c.get(Calendar.DAY_OF_MONTH);
        
        updateDisplay(1);
        updateDisplay(2);
	}

	private void updateDisplay(int flag) {
		if(flag == 1) {
			sYear = tYear;
			sMonth = tMonth;
			sDay = tDay;
			btn_start_date.setText(new StringBuilder()
			.append(sMonth+1).append("-")
			.append(sDay).append("-")
			.append(sYear));
		}
		else if(flag == 2) {
			eYear = tYear;
			eMonth = tMonth;
			eDay = tDay;
			btn_end_date.setText(new StringBuilder()
			.append(eMonth+1).append("-")
			.append(eDay).append("-")
			.append(eYear));
		}
	};

	private DatePickerDialog.OnDateSetListener mDateSetListener = 
			new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			// TODO Auto-generated method stub
			tYear = year;
			tMonth = monthOfYear;
			tDay = dayOfMonth;
			updateDisplay(flag);
		}
	};

	protected Dialog onCreateDialog(int id)
	{
		switch(id)
		{
		case DATE_DIALOG_ID1:
			flag = 1;
			return new DatePickerDialog(this, mDateSetListener, sYear, sMonth, sDay);
		case DATE_DIALOG_ID2:
			flag = 2;
			return new DatePickerDialog(this, mDateSetListener, eYear, eMonth, eDay);
		}
		return null;
	}
}