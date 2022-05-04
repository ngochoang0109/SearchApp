import { Link } from "react-router-dom";

const Documents = () => {
    return (
        <div className="card-body">
            <Link className="btn btn-primary" to={'/create-document'}>Tạo index </Link>
            {/* <strong>article</strong> */}
            <div className="alert alert-danger mt-4">
                {/* <?=$mgs?> */}
                
            </div>
        </div>)
}

export default Documents;