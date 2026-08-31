// protected route
const getUserProfile = (req, res) => {
  res.json({ message: "Profile accessed", user: req.user });
};

export default getUserProfile;