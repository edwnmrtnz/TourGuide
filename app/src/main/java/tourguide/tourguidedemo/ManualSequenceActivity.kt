package tourguide.tourguidedemo

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_in_sequence.*
import tourguide.tourguide.Overlay
import tourguide.tourguide.Pointer
import tourguide.tourguide.TourGuide
import java.lang.IllegalStateException

/**
 * InSequenceActivity demonstrates how to use TourGuide in sequence one after another
 */
class ManualSequenceActivity : AppCompatActivity() {
    lateinit var tourGuide: TourGuide

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_in_sequence)

        /* setup enter and exit animation */
        val enterAnimation = AlphaAnimation(0f, 1f)
                .apply {
                    duration = 600
                    fillAfter = true
                }


        val exitAnimation = AlphaAnimation(1f, 0f)
                .apply {
                    duration = 600
                    fillAfter = true
                }

        /* initialize TourGuide without playOn() */
        tourGuide = TourGuide.create(this) {
            toolTip {
                gravity { Gravity.END }
                title { "Hey!" }
                description { "I'm the top fellow" }
                btnText = "Hello"
                setBackgroundColor(Color.parseColor("#f5f5f5"))
                setTextColor(Color.parseColor("#474747"))
            }
            apply {
                overlay {
                    style { Overlay.Style.RECTANGLE }
                    setEnterAnimation(enterAnimation)
                    setExitAnimation(exitAnimation)
                    setOnClickListener(View.OnClickListener { nextNt() })
                }
            }
        }
        tourGuide.btnClick?.setOnClickListener {
            Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show()
        }

        tourGuide.playOn(button)



        /* setup 1st button, when clicked, cleanUp() and re-run TourGuide on button2 */
        button.setOnClickListener {
            tourGuide.apply {
                cleanUp()
                toolTip {
                    title { "Hey there!" }
                    description { "Just the middle man" }
                    gravity { Gravity.BOTTOM or Gravity.LEFT }
                }
            }.playOn(button2)
        }

        /* setup 2nd button, when clicked, cleanUp() and re-run TourGuide on button3 */
        button2.setOnClickListener {
            tourGuide.apply {
                cleanUp()
                toolTip {
                    title { "Hey..." }
                    description { "It's time to say goodbye" }
                    gravity { Gravity.TOP or Gravity.RIGHT }
                }
            }.playOn(button3)
        }

        /* setup 3rd button, when clicked, run cleanUp() */
        button3.setOnClickListener { tourGuide.cleanUp() }

    }

    private fun nextNt() {
        tourGuide.apply {
            cleanUp()
            toolTip {
                title { "Hey there!" }
                description { "Just the middle man" }
                gravity { Gravity.BOTTOM or Gravity.LEFT }
            }
        }.playOn(button2)
    }
}
