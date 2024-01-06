package pmoschos.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultDialog extends Dialog {
    private String message;
    private MainActivity mainActivity;
    private TextView messageTV;
    private Button restartBtn;

    public ResultDialog(Context context, String message, MainActivity mainActivity) {
        super(context);
        this.message = message;
        this.mainActivity = mainActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_dialog);

        messageTV = findViewById(R.id.messageTV);
        restartBtn = findViewById(R.id.restartBtn);

        messageTV.setText(message);

        restartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.restartMatch();
                dismiss();
            }
        });

    }
}
