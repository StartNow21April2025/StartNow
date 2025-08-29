import { API_BASE_URL } from "../config/api.js";

export default function SignInModal({ onClose, onLoginSuccess }) {
  const handleSubmit = async (e) => {
    e.preventDefault();

    const form = e.target;
    if (!form.checkValidity()) {
      form.reportValidity();
      return;
    }

    const email = form.email.value;
    const password = form.password.value;

    try {
      const res = await fetch(`${API_BASE_URL}/api/auth/sign-in`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, password }),
        credentials: "include",
      });

      if (!res.ok) {
        const error = await res.text();
        alert("Sign-in failed: " + error);
        return;
      }

      const data = await res.json();
      console.log("Login success", data);
      alert("Welcome!");

      if (onLoginSuccess) {
        onLoginSuccess(data); // update parent with user data
      }
    } catch (err) {
      console.error("Login error", err);
      alert("Something went wrong");
    }
  };

  return (
    <div className="fixed inset-0 bg-black bg-opacity-60 flex items-center justify-center z-50">
      <form
        onSubmit={handleSubmit}
        className="bg-white rounded-lg p-6 w-full max-w-sm"
      >
        <h2 className="text-xl font-bold mb-4 text-gray-800">Sign In</h2>

        <input
          type="email"
          placeholder="Email"
          name="email"
          required
          className="w-full mb-3 p-2 border rounded text-black"
        />

        <input
          type="password"
          placeholder="Password (min 8 characters)"
          name="password"
          required
          minLength={8}
          className="w-full mb-3 p-2 border rounded text-black"
        />

        <button
          type="submit"
          className="bg-green-500 text-white w-full py-2 rounded hover:bg-green-600"
        >
          Sign In
        </button>

        <button
          type="button"
          onClick={onClose}
          className="mt-3 w-full text-sm text-gray-600 hover:underline"
        >
          Cancel
        </button>
      </form>
    </div>
  );
}
