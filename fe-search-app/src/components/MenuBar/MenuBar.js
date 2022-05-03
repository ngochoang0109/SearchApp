import { Link } from "react-router-dom";

const MenuBar = () => {
    return (
        <nav id="sidebar">
            <div className="custom-menu">
                <button type="button" id="sidebarCollapse" className="btn btn-primary">
                    <i className="fa fa-bars"></i>
                    <span className="sr-only">Toggle Menu</span>
                </button>
            </div>
            <h1><a href="#/" className="logo">Java-Search Engine</a></h1>
            <ul className="list-unstyled components mb-5">
                <li className="active">
                    <Link to={'/home-document'}><span className="fa fa-home mr-3"></span> Homepage</Link>
                </li>
                <li>
                    <Link to={'/management-document'}><span className="fa fa-user mr-3"></span> Quản lý index</Link>
                </li>
                <li>
                    <Link to={'/create-document'}><span className="fa fa-sticky-note mr-3"></span> Document</Link>
                </li>
                <li>
                    <Link to={'/search-document'}><span className="fa fa-sticky-note mr-3"></span> Tìm kiếm</Link>
                </li>
            </ul>

        </nav>
    )
}

export default MenuBar;