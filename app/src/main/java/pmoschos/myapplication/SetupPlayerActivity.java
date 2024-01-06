package pmoschos.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SetupPlayerActivity extends AppCompatActivity {

    private EditText playerOne;
    private EditText playerTwo;
    private Button startGameBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_player);

        playerOne = findViewById(R.id.playerOne);
        playerTwo = findViewById(R.id.playerTwo);
        startGameBtn = findViewById(R.id.startGameButton);

        startGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String player1 = playerOne.getText().toString();
                String player2 = playerTwo.getText().toString();

                if (player1.isEmpty() || player2.isEmpty()) {
                    Toast.makeText(SetupPlayerActivity.this, "Please enter player name", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(SetupPlayerActivity.this, MainActivity.class);
                    intent.putExtra("playerOne", player1);
                    intent.putExtra("playerTwo", player2);
                    startActivity(intent);
                }
            }
        });
    }
}