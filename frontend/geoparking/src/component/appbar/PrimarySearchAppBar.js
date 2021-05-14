import { Button } from "@material-ui/core";
import AppBar from "@material-ui/core/AppBar";
import Badge from "@material-ui/core/Badge";
import IconButton from "@material-ui/core/IconButton";
import InputBase from "@material-ui/core/InputBase";
import Menu from "@material-ui/core/Menu";
import MenuItem from "@material-ui/core/MenuItem";
import { fade, makeStyles } from "@material-ui/core/styles";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import { Home, LocalParking } from "@material-ui/icons";
import AccountCircle from "@material-ui/icons/AccountCircle";
import MoreIcon from "@material-ui/icons/MoreVert";
import NotificationsIcon from "@material-ui/icons/Notifications";
import SearchIcon from "@material-ui/icons/Search";
import React, { useEffect, useState } from "react";
import { Link, useHistory } from "react-router-dom";
import { useAuth } from "../../authentication/ProvideAuth";
import logo from "../../logo.svg";

const useStyles = makeStyles((theme) => ({
	offset: theme.mixins.toolbar,
	grow: {
		flexGrow: 1,
	},
	menuButton: {
		marginRight: theme.spacing(0),
	},
	title: {
		display: "none",
		[theme.breakpoints.up("sm")]: {
			display: "block",
		},
	},
	search: {
		position: "relative",
		borderRadius: theme.shape.borderRadius,
		backgroundColor: fade(theme.palette.common.white, 0.15),
		"&:hover": {
			backgroundColor: fade(theme.palette.common.white, 0.25),
		},
		marginRight: theme.spacing(2),
		marginLeft: 0,
		width: "100%",
		[theme.breakpoints.up("sm")]: {
			marginLeft: theme.spacing(3),
			width: "auto",
		},
	},
	searchIcon: {
		padding: theme.spacing(0, 2),
		height: "100%",
		position: "absolute",
		pointerEvents: "none",
		display: "flex",
		alignItems: "center",
		justifyContent: "center",
	},
	inputRoot: {
		color: "inherit",
	},
	inputInput: {
		padding: theme.spacing(1, 1, 1, 0),
		// vertical padding + font size from searchIcon
		paddingLeft: `calc(1em + ${theme.spacing(4)}px)`,
		transition: theme.transitions.create("width"),
		width: "100%",
		[theme.breakpoints.up("md")]: {
			width: "20ch",
		},
	},
	sectionDesktop: {
		display: "none",
		[theme.breakpoints.up("md")]: {
			display: "flex",
		},
	},
	sectionMobile: {
		display: "flex",
		[theme.breakpoints.up("md")]: {
			display: "none",
		},
	},
	link: {
		textDecoration: "none",
		color: "inherit",
		width: "100%",
		height: "100%",
	},
}));

export default function PrimarySearchAppBar() {
	const auth = useAuth();
	const history = useHistory();

	const [isUserAuthenticated, setIsUserAuthenticated] = useState(
		auth.isUserLoggedIn || auth.isAdminLoggedIn
	);

	useEffect(() => {
		// console.log(auth);
		setIsUserAuthenticated(
			() => auth.isUserLoggedIn || auth.isAdminLoggedIn
		);
	}, [auth]);

	const logoutCallback = () => {
		history.push("/");
		setIsUserAuthenticated(false);
	};

	const classes = useStyles();
	const [anchorEl, setAnchorEl] = React.useState(null);
	const [mobileMoreAnchorEl, setMobileMoreAnchorEl] = React.useState(null);

	const isMenuOpen = Boolean(anchorEl);
	const isMobileMenuOpen = Boolean(mobileMoreAnchorEl);

	const handleProfileMenuOpen = (event) => {
		setAnchorEl(event.currentTarget);
	};

	const handleMobileMenuClose = () => {
		setMobileMoreAnchorEl(null);
	};

	const handleMenuClose = () => {
		setAnchorEl(null);
		handleMobileMenuClose();
	};

	const handleMobileMenuOpen = (event) => {
		setMobileMoreAnchorEl(event.currentTarget);
	};

	const isUserLogged = () => {
		if (auth.isUserLoggedIn && !auth.isAdminLoggedIn) return true;
		return false;
	};

	const isAdminLogged = () => {
		if (!auth.isUserLoggedIn && auth.isAdminLoggedIn) return true;
		return false;
	};

	const menuId = "primary-search-account-menu";
	const renderMenu = (
		<Menu
			anchorEl={anchorEl}
			anchorOrigin={{ vertical: "top", horizontal: "right" }}
			id={menuId}
			keepMounted
			transformOrigin={{ vertical: "top", horizontal: "right" }}
			open={isMenuOpen}
			onClose={handleMenuClose}
		>
			<Link to="/my-bookings" className={classes.link}>
				<MenuItem onClick={handleMenuClose}>My bookings</MenuItem>
			</Link>

			<Link to="/my-account" className={classes.link}>
				<MenuItem onClick={handleMenuClose}>Profile</MenuItem>
			</Link>
		</Menu>
	);

	const mobileMenuId = "primary-search-account-menu-mobile";
	const renderMobileMenu = (
		<Menu
			anchorEl={mobileMoreAnchorEl}
			anchorOrigin={{ vertical: "top", horizontal: "right" }}
			id={mobileMenuId}
			keepMounted
			transformOrigin={{ vertical: "top", horizontal: "right" }}
			open={isMobileMenuOpen}
			onClose={handleMobileMenuClose}
		>
			<MenuItem>
				<IconButton aria-label="show 4 new mails" color="inherit">
					<Badge badgeContent={4} color="secondary">
						<NotificationsIcon />
					</Badge>
				</IconButton>
				<p>Notification</p>
			</MenuItem>

			{isUserLogged() && (
				<div>
					<Link to="/my-bookings" className={classes.link}>
						<MenuItem>
							<IconButton
								aria-label="show 11 new notifications"
								color="inherit"
							>
								<Badge badgeContent={1} color="secondary">
									<LocalParking />
								</Badge>
							</IconButton>
							<p>My bookings</p>
						</MenuItem>
					</Link>

					<Link to="/my-account" className={classes.link}>
						<MenuItem>
							<IconButton
								aria-label="account of current user"
								aria-controls="primary-search-account-menu"
								aria-haspopup="true"
								color="inherit"
							>
								<AccountCircle />
							</IconButton>
							<p>Profile</p>
						</MenuItem>
					</Link>
				</div>
			)}

			{isAdminLogged() && (
				<div>
					<Link to="/admin" className={classes.link}>
						<MenuItem>
							<IconButton
								aria-label="account of current user"
								aria-controls="primary-search-account-menu"
								aria-haspopup="true"
								color="inherit"
							>
								<Home />
							</IconButton>
							<p>Dashboard</p>
						</MenuItem>
					</Link>

					<Link to="/admin/my-account" className={classes.link}>
						<MenuItem>
							<IconButton
								aria-label="account of current user"
								aria-controls="primary-search-account-menu"
								aria-haspopup="true"
								color="inherit"
							>
								<AccountCircle />
							</IconButton>
							<p>Profile</p>
						</MenuItem>
					</Link>
				</div>
			)}

			<hr style={{ width: "80%" }} />
			<MenuItem>
				<LogoutBtn signout={() => auth.signout(logoutCallback)} />
			</MenuItem>
		</Menu>
	);

	return (
		<div className={classes.grow}>
			<AppBar position="fixed">
				<Toolbar>
					<IconButton
						edge="start"
						className={classes.menuButton}
						color="inherit"
						aria-label="open drawer"
						onClick={() => history.push("/")}
					>
						<img height="40px" src={logo} alt="Logo" />
					</IconButton>
					<Typography className={classes.title} variant="h6" noWrap>
						<Link to="/" className={classes.link}>
							GeoParking
						</Link>
					</Typography>
					<div className={classes.search}>
						<div className={classes.searchIcon}>
							<SearchIcon />
						</div>
						<InputBase
							placeholder="Search…"
							classes={{
								root: classes.inputRoot,
								input: classes.inputInput,
							}}
							inputProps={{ "aria-label": "search" }}
						/>
					</div>
					<div className={classes.grow} />

					{!isUserAuthenticated ? (
						<Link to="/login" style={{ textDecoration: "none" }}>
							<Button variant={"contained"}>Sign in/up</Button>
						</Link>
					) : (
						<>
							<div className={classes.sectionDesktop}>
								{/* <IconButton
									aria-label="show 4 new mails"
									color="inherit"
								>
									<Badge badgeContent={4} color="secondary">
										<ReceiptOutlined />
									</Badge>
								</IconButton> */}

								{isUserLogged() && (
									<>
										<Link to="/" className={classes.link}>
											<IconButton
												edge="end"
												aria-label="home of admin"
												aria-haspopup="false"
												color="inherit"
											>
												<Home />
											</IconButton>
										</Link>
										<IconButton
											edge="end"
											aria-label="account of current user"
											aria-controls={menuId}
											aria-haspopup="true"
											onClick={handleProfileMenuOpen}
											color="inherit"
										>
											<AccountCircle />
										</IconButton>
									</>
								)}

								{isAdminLogged() && (
									<>
										<Link
											to="/admin"
											className={classes.link}
										>
											<IconButton
												edge="end"
												aria-label="home of admin"
												aria-haspopup="false"
												color="inherit"
											>
												<Home />
											</IconButton>
										</Link>
										<Link
											to="/admin/my-account"
											className={classes.link}
										>
											<IconButton
												edge="end"
												aria-label="account of current user"
												aria-haspopup="false"
												color="inherit"
											>
												<AccountCircle />
											</IconButton>
										</Link>
									</>
								)}

								<IconButton
									aria-label="show 17 new notifications"
									color="inherit"
								>
									<Badge badgeContent={17} color="secondary">
										<NotificationsIcon />
									</Badge>
								</IconButton>

								<div
									style={{
										display: "flex",
										alignItems: "center",
									}}
								>
									<LogoutBtn
										signout={() =>
											auth.signout(logoutCallback)
										}
									/>
								</div>
							</div>
							<div className={classes.sectionMobile}>
								<IconButton
									aria-label="show more"
									aria-controls={mobileMenuId}
									aria-haspopup="true"
									onClick={handleMobileMenuOpen}
									color="inherit"
								>
									<MoreIcon />
								</IconButton>
							</div>
						</>
					)}
				</Toolbar>
			</AppBar>
			{isUserAuthenticated && (
				<>
					{renderMobileMenu} {renderMenu}
				</>
			)}
			<div className={classes.offset} />
		</div>
	);
}

function LogoutBtn({ signout }) {
	return (
		<Button
			onClick={() => signout()}
			variant="contained"
			color="secondary"
			style={{
				marginLeft: 20,
				fontFamily: "sans-serif",
				fontWeight: "bold",
			}}
		>
			Logout
		</Button>
	);
}
