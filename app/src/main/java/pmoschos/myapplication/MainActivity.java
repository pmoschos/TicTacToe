package pmoschos.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int BOARD_SIZE = 9;
    private final List<int[]> winningCombinations = new ArrayList<>();
    private int[] boxStates = new int[BOARD_SIZE]; // Stores the state of each box
    private int currentPlayer = 1; // 1 for Player One, 2 for Player Two
    private int totalMoves = 0;

    private TextView playerOneNameTextView;
    private TextView playerTwoNameTextView;
    private LinearLayout playerOneLayout;
    private LinearLayout playerTwoLayout;
    private final ImageView[] boxImageViews = new ImageView[BOARD_SIZE];
    private TextView scoreP1;
    private TextView scoreP2;
    private int score1 = 0;
    private int score2 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        initializeGame();
        setBoxClickListeners();
    }

    private void initializeViews() {
        playerOneNameTextView = findViewById(R.id.playerOneName);
        playerTwoNameTextView = findViewById(R.id.playerTwoName);
        playerOneLayout = findViewById(R.id.playerOneLayout);
        playerTwoLayout = findViewById(R.id.playerTwoLayout);
        scoreP1 = findViewById(R.id.scoreP1);
        scoreP2 = findViewById(R.id.scoreP2);

        playerOneLayout.setBackgroundResource(R.drawable.black_border);

        playerOneNameTextView.setText(getIntent().getStringExtra("playerOne"));
        playerTwoNameTextView.setText(getIntent().getStringExtra("playerTwo"));
    }

    private void initializeGame() {
        winningCombinations.add(new int[]{0, 1, 2});
        winningCombinations.add(new int[]{3, 4, 5});
        winningCombinations.add(new int[]{6, 7, 8});
        winningCombinations.add(new int[]{0, 3, 6});
        winningCombinations.add(new int[]{1, 4, 7});
        winningCombinations.add(new int[]{2, 5, 8});
        winningCombinations.add(new int[]{0, 4, 8});
        winningCombinations.add(new int[]{2, 4, 6});
    }

    private void setBoxClickListeners() {
        for (int i = 0; i < boxImageViews.length; i++) {
            String boxID = "image" + (i + 1);
            int resID = getResources().getIdentifier(boxID, "id", getPackageName());
            boxImageViews[i] = findViewById(resID);
            int finalI = i;
            boxImageViews[i].setOnClickListener(view -> onBoxClicked(finalI));
        }
    }

    private void onBoxClicked(int boxIndex) {
        if (isBoxSelectable(boxIndex)) {
            performAction(boxImageViews[boxIndex], boxIndex);
        }
    }

    private void performAction(ImageView imageView, int selectedBoxIndex) {
        boxStates[selectedBoxIndex] = currentPlayer;

        int imageRes = (currentPlayer == 1) ? R.drawable.ximage : R.drawable.oimage;
        imageView.setImageResource(imageRes);

        if (checkWinner()) {
            String winnerName = (currentPlayer == 1) ? playerOneNameTextView.getText().toString() : playerTwoNameTextView.getText().toString();
            showResultDialog(winnerName + " is a Winner!");
            if (currentPlayer == 1) {
                scoreP1.setText(++score1 + "");
            } else {
                scoreP2.setText(++score2 + "");
            }
        } else if (totalMoves == BOARD_SIZE - 1) {
            showResultDialog("Match Draw");
        } else {
            changePlayer();
        }
    }

    private void changePlayer() {
        currentPlayer = (currentPlayer == 1) ? 2 : 1;
        totalMoves++;
        updatePlayerLayouts();
    }

    private void updatePlayerLayouts() {
        if (currentPlayer == 1) {
            playerOneLayout.setBackgroundResource(R.drawable.black_border);
            playerTwoLayout.setBackgroundResource(R.drawable.white_box);
        } else {
            playerTwoLayout.setBackgroundResource(R.drawable.black_border);
            playerOneLayout.setBackgroundResource(R.drawable.white_box);
        }
    }

    private boolean checkWinner() {
        for (int[] combination : winningCombinations) {
            if (boxStates[combination[0]] == currentPlayer &&
                    boxStates[combination[1]] == currentPlayer &&
                    boxStates[combination[2]] == currentPlayer) {
                return true;
            }
        }
        return false;
    }

    private boolean isBoxSelectable(int boxIndex) {
        return boxStates[boxIndex] == 0;
    }

    private void showResultDialog(String message) {
        ResultDialog resultDialog = new ResultDialog(MainActivity.this, message, MainActivity.this);
        resultDialog.setCancelable(false);

        // Show the dialog first
        resultDialog.show();

        // Then get the Window of the dialog and set its gravity to top
        Window window = resultDialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.gravity = Gravity.TOP;
            window.setAttributes(layoutParams);
        }
    }

    public void restartMatch() {
        boxStates = new int[BOARD_SIZE]; // Resetting box states
        currentPlayer = 1;
        totalMoves = 0;
        resetBoxImages();
        updatePlayerLayouts();
    }

    private void resetBoxImages() {
        for (ImageView imageView : boxImageViews) {
            imageView.setImageResource(R.drawable.white_box); // Replace with your default or empty image
        }
    }
}
