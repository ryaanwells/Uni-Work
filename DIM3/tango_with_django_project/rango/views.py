from django.template import RequestContext, loader
from django.http import HttpResponse
from django.shortcuts import render_to_response
from rango.models import Category, Page, CategoryForm, PageForm

def index(request):
    template = loader.get_template('rango/index.html')
    
    cat_list = Category.objects.all()
    for cat in cat_list:
        category_name = cat.name
        cat.url = encode_category(category_name)
    
    context = RequestContext(request, {'cat_list': cat_list})
    return HttpResponse(template.render(context))

def category(request, category_name_url):
    template = loader.get_template('rango/category.html')
    
    category_name = decode_category(category_name_url)
    context_dict = {'category_name_url' : category_name_url,
                    'category_name' : category_name}
    
    cat = Category.objects.filter(name=category_name)

    if cat:
        pages = Page.objects.filter(category=cat)
        context_dict['pages']=pages
        
    context = RequestContext(request,context_dict)
    return HttpResponse(template.render(context))

def add_category(request):
        # immediately get the context - as it may contain posting data
        context = RequestContext(request)
        if request.method == 'POST':
                # data has been entered into the form via Post
                form = CategoryForm(request.POST)
                if form.is_valid():
                        # the form has been correctly filled in,
                        # so lets save the data to the model
                        cat = form.save(commit=True)
                        # show the index page with the list of categories
                        return index(request)
                else:
                        # the form contains errors,
                        # show the form again, with error messages
                        pass
        else:
                # a GET request was made, so we simply show a blank/empty form.
                form = CategoryForm()

        # pass on the context, and the form data.
        return render_to_response('rango/add_category.html',
                {'form': form }, context)

def add_page(request, category_name_url):
        context = RequestContext(request)

        category_name = decode_category(category_name_url)
        if request.method == 'POST':
                form = PageForm(request.POST)
                if form.is_valid():
                        # this time we cant commit straight away
                        # because not all fields are populated
                        page = form.save(commit=False)
                        # retrieve and assign the category object to the new page
                        cat = Category.objects.get(name=category_name)
                        page.category = cat
                        # also plug in a default value for the no. of page views
                        page.views = 0
                        page.save()
                        # Now that the page is saved, display the category instead.
                        return category(request, category_name)
                else:
                        print form.errors
        else:
                form = PageForm()

        return render_to_response( 'rango/add_page.html',
                        {'category_name_url': category_name_url,
                                'category_name': category_name, 'form': form },
                        context)

def encode_category(category_name):
        # returns the name converted for insert into url
        return category_name.replace(' ','_')

def decode_category(category_url):
        # returns the category name given the category url portion
        return category_url.replace('_',' ')
