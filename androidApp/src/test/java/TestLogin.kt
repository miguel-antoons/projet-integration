import com.example.smartfridge.android.VerifyEmailPassword.validateForm
import org.junit.Assert.*
import org.junit.Test

class TestLogin {
    @Test
    fun validateEmailPassword() {
        assertTrue(validateForm("example@gmal.com", "test1234test"))
        assertFalse(validateForm("example@gmal.com", "test1"))
        assertFalse(validateForm("", "test1"))
        assertFalse(validateForm("example@gmal.com", ""))
    }
}